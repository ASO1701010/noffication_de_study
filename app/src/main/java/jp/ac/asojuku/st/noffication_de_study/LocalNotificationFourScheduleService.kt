package jp.ac.asojuku.st.noffication_de_study

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import jp.ac.asojuku.st.noffication_de_study.db.AnswersOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class LocalNotificationFourScheduleService : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        // ここに通知の処理

        // 通知をAlarmManagerに登録する
        this.registerNotice(context)

        //通知表示
        this.showNotification(context)
    }

    fun registerNotice(context: Context) {
        // 設定情報の読み込み
        val spGetter = context.getSharedPreferences("user_data", MODE_PRIVATE)
        val startTime = spGetter.getString("NDS_Start", "09:00") as String
        val endTime = spGetter.getString("NDS_End", "21:00") as String
        val endTimeInt = Integer.valueOf(endTime.replace(":", ""))
        val startTimeList: List<String> = startTime.split(Regex(":"))

        if (spGetter.getBoolean("NDS_check", false)) {
            // 通知で出題する
            val spaceTime = spGetter.getString("NDS_Interval", "5") as String

            val intent = Intent(context, LocalNotificationFourScheduleService::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            val sdf = SimpleDateFormat("HHmm", Locale.getDefault())
            val nowTime = sdf.format(calendar.time) as String

            if (endTimeInt <= nowTime.toInt()) {
                // 現在の時間が出題する最終時間を超えていたら次の日に出題
                calendar.add(Calendar.DATE, 1)
                calendar.set(Calendar.HOUR, startTimeList[0].toInt())
                calendar.set(Calendar.MINUTE, startTimeList[1].toInt())
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            } else {
                // 現在の時間に通知間隔を加算して出題
                calendar.add(Calendar.SECOND, spaceTime.toInt())
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            // 通知をキャンセル（できるはず）
            val intent = Intent(context, LocalNotificationFourScheduleService::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(context: Context) {
        val question_id = Random.nextInt(80) + 1

        val db = SQLiteHelper(context).readableDatabase
        val dbHelper = QuestionsOpenHelper(db)

        var question_text = dbHelper.find_question(question_id)
        if (question_text == null) {
            return
        }
        question_text[1].replace("&quot;".toRegex(), "\"")
        question_text[2].replace("&quot;".toRegex(), "\"")


        val mChannel = NotificationChannel("0", "問題通知", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        val contentView = RemoteViews(context.packageName, R.layout.notifi_layout)


        val intent1 = Intent(context, AnswerActivity::class.java)
        val intent2 = Intent(context, AnswerActivity::class.java)
        val intent3 = Intent(context, AnswerActivity::class.java)
        val intent4 = Intent(context, AnswerActivity::class.java)
        val intentQuestion = Intent(context, QuestionActivity::class.java)

        // 通知ボタンタップ時に渡す値
        intent1.putExtra("question_id", question_id)
        intent2.putExtra("question_id", question_id)
        intent3.putExtra("question_id", question_id)
        intent4.putExtra("question_id", question_id)

        // ExamDataを生成＆整形
        val data1 = ExamData(1, "", "")
        data1.set_list_data(arrayListOf(question_id))
        val data2 = ExamData(1, "", "")
        data2.set_list_data(arrayListOf(question_id))
        val data3 = ExamData(1, "", "")
        data3.set_list_data(arrayListOf(question_id))
        val data4 = ExamData(1, "", "")
        data4.set_list_data(arrayListOf(question_id))
        val data5 = ExamData(1, "", "")
        data5.set_list_data(arrayListOf(question_id))
        data1.question_current = question_id
        data2.question_current = question_id
        data3.question_current = question_id
        data4.question_current = question_id
        data5.question_current = question_id
        data1.question_next = question_id
        data2.question_next = question_id
        data3.question_next = question_id
        data4.question_next = question_id
        data5.question_next = question_id
        regAnswer(0, data1, context)
        regAnswer(1, data2, context)
        regAnswer(2, data3, context)
        regAnswer(3, data4, context)

        // 生成したExamDataをインテントに登録
        intent1.putExtra("exam_data", data1)
        intent2.putExtra("exam_data", data2)
        intent3.putExtra("exam_data", data3)
        intent4.putExtra("exam_data", data4)
        intentQuestion.putExtra("exam_data", data5)

        // 各intentをPendingIntentに変換
        val pi1 = PendingIntent.getActivity(context, 1983418741, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton, pi1)
        val pi2 = PendingIntent.getActivity(context, 1983418742, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton2, pi2)
        val pi3 = PendingIntent.getActivity(context, 1983418743, intent3, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton3, pi3)
        val pi4 = PendingIntent.getActivity(context, 1983418744, intent4, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton4, pi4)
        val piQuestion =
            PendingIntent.getActivity(context, 1983418745, intentQuestion, PendingIntent.FLAG_CANCEL_CURRENT)

        // 表示するテキスト
        contentView.setTextViewText(R.id.notifiText, question_text.get(1))

        // 表示内容を生成
        val notification = NotificationCompat.Builder(context, "0")
            .setCustomBigContentView(contentView)
            .setContentTitle("問題ですが何か？？")
            .setContentIntent(piQuestion)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        // 表示
        notificationManager.notify(question_id, notification)
    }

    fun regAnswer(choice_number: Int, examData: ExamData, context: Context) {
        //自分の解答を登録
        examData.answered_list.add(choice_number)
        //正解をDBから取得
        val answers = SQLiteHelper(context)
        val db = answers.readableDatabase
        val AOH = AnswersOpenHelper(db)
        val answer = AOH.find_answers(examData.question_current)?.get(1) //正しい正解
        var isCorrected = false //正解だった場合にtrueにする
        if (choice_number == answer) {
            isCorrected = true
        }
        examData.isCorrect_list.add(isCorrected)//解いた問題が正解だったかどうかがBoolean型で入る
    }

}
