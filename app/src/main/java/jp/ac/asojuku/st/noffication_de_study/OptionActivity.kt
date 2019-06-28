package jp.ac.asojuku.st.noffication_de_study

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import jp.ac.asojuku.st.noffication_de_study.db.AnswersOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import kotlinx.android.synthetic.main.activity_option.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import kotlin.random.Random

class OptionActivity : AppCompatActivity() {
    // Google SignIn
    private val rcSignIn = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var spEditor: SharedPreferences.Editor
    private lateinit var spGetter: SharedPreferences

    private val noticeIntervalItems = arrayOf("5", "10", "15", "20", "25", "30")

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        //通知のテストデータ
        showNotification(1)

        spEditor = getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()

        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, noticeIntervalItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        OA_Noffication_Interval.adapter = adapter

        // SharedPreferencesから設定情報を取得し画面に反映
        spGetter = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        OA_NDS_Mode_BTN.isChecked = spGetter.getBoolean("NDS_check", false)
        OA_SDS_Mode_BTN.isChecked = spGetter.getBoolean("SDS_check", false)
        OA_Noffication_Time_Between1.text = spGetter.getString("NDS_Start", "09:00")
        OA_Noffication_Time_Between2.text = spGetter.getString("NDS_End", "21:00")
        OA_Noffication_Interval.setSelection(noticeIntervalItems.indexOf(spGetter.getString("NDS_Interval", "5")))

        // Google SignIn
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onResume() {
        super.onResume()

        OA_Back_BTN.setSafeClickListener {
            finish()
        }

        OA_NDS_Mode_BTN.setSafeClickListener {
            spEditor.putBoolean("NDS_check", OA_NDS_Mode_BTN.isChecked).apply()
        }

        OA_SDS_Mode_BTN.setSafeClickListener {
            spEditor.putBoolean("SDS_check", OA_SDS_Mode_BTN.isChecked).apply()
        }

        OA_Noffication_Time_Between1.setSafeClickListener {
            val nowTime = spGetter.getString("NDS_Start", "09:00")
            val nowTimeList: List<String> =
                if (nowTime.isNullOrEmpty()) listOf("21", "00") else nowTime.split(Regex(":"))
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfDay ->
                    val time = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                    OA_Noffication_Time_Between1.text = time
                    spEditor.putString("NDS_Start", time).apply()
                }, nowTimeList[0].toInt(), nowTimeList[1].toInt(), true
            ).show()
        }

        OA_Noffication_Time_Between2.setSafeClickListener {
            val nowTime = spGetter.getString("NDS_End", "21:00")
            val nowTimeList: List<String> =
                if (nowTime.isNullOrEmpty()) listOf("21", "00") else nowTime.split(Regex(":"))
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfDay ->
                    val time = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                    OA_Noffication_Time_Between2.text = time
                    spEditor.putString("NDS_End", time).apply()
                }, nowTimeList[0].toInt(), nowTimeList[1].toInt(), true
            ).show()
        }

        OA_Noffication_Interval.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinner = parent as Spinner
                val select = spinner.selectedItem.toString()
                spEditor.putString("NDS_Interval", select).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Google SignIn
        sign_in_button.setSafeClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, rcSignIn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == rcSignIn) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Googleアカウントのログインに失敗しました", Toast.LENGTH_LONG).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "認証に失敗しました", Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "[${user.displayName}]さんでログインしています", Toast.LENGTH_LONG).show()
            ApiPostTask {
                if (it.isNullOrEmpty()) {
                    Toast.makeText(this, "APIとの通信に失敗しました", Toast.LENGTH_LONG).show()
                } else {
                    val jsonObject = JSONObject(it)
                    val status = jsonObject.getString("status")
                    if (status == "S00") {
                        val userId = jsonObject.getJSONObject("data").getString("user_id")
                        val e: SharedPreferences.Editor = getSharedPreferences("user_data", MODE_PRIVATE).edit()
                        e.putString("user_id", userId).apply()
                    } else {
                        Toast.makeText(this, "ユーザー登録に失敗しました", Toast.LENGTH_LONG).show()
                    }
                }
            }.execute("add-user.php", hashMapOf("token" to user.uid).toString())
        }
    }

//    fun getRandomQuestion(): Int{
//        val randomInt = Random.nextInt(10)
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(question_id: Int) {
        val db = SQLiteHelper(this).readableDatabase
        val dbHelper = QuestionsOpenHelper(db)

        var question_text = dbHelper.find_question(question_id)
        if(question_text == null){
            return
        }
        val mChannel = NotificationChannel("0","問題通知", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        val contentView = RemoteViews(getPackageName(), R.layout.notifi_layout)


        val intent1 = Intent(this, AnswerActivity::class.java)
        val intent2 = Intent(this, AnswerActivity::class.java)
        val intent3 = Intent(this, AnswerActivity::class.java)
        val intent4 = Intent(this, AnswerActivity::class.java)
        val intentQuestion = Intent(this, QuestionActivity::class.java)

        // 通知ボタンタップ時に渡す値
        intent1.putExtra("question_id",question_id)
        intent2.putExtra("question_id",question_id)
        intent3.putExtra("question_id",question_id)
        intent4.putExtra("question_id",question_id)

        val data1 = ExamData(1,"","")
        data1.set_list_data(arrayListOf(question_id))
        val data2 = ExamData(1,"","")
        data2.set_list_data(arrayListOf(question_id))
        val data3 = ExamData(1,"","")
        data3.set_list_data(arrayListOf(question_id))
        val data4 = ExamData(1,"","")
        data4.set_list_data(arrayListOf(question_id))
        val data5 = ExamData(1,"","")
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

        regAnswer(0,data1)
        regAnswer(1,data2)
        regAnswer(2,data3)
        regAnswer(3,data4)


        intent1.putExtra("exam_data",data1)
        intent2.putExtra("exam_data",data2)
        intent3.putExtra("exam_data",data3)
        intent4.putExtra("exam_data",data4)
        intentQuestion.putExtra("exam_data",data5)



        // 各intentをPendingIntentに変換
        val pi1 = PendingIntent.getActivity(this, 1983418741, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton, pi1)
        val pi2 = PendingIntent.getActivity(this, 1983418742, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton2, pi2)
        val pi3 = PendingIntent.getActivity(this, 1983418743, intent3, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton3, pi3)
        val pi4 = PendingIntent.getActivity(this, 1983418744, intent4, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton4, pi4)

        val piQuestion = PendingIntent.getActivity(this,1983418745, intentQuestion, PendingIntent.FLAG_CANCEL_CURRENT)

        // 表示するテキスト
        contentView.setTextViewText(R.id.notifiText,question_text.get(1))

        // 表示内容を生成
        val notification = NotificationCompat.Builder(this,"0")
            .setCustomBigContentView(contentView)
            .setContentTitle("問題ですが何か？？")
            .setContentIntent(piQuestion)
            .setSmallIcon(R.drawable.abc_ic_star_half_black_16dp)
            .build()

        // 表示
        notificationManager.notify(0,notification)
    }
    fun regAnswer(choice_number: Int, examData: ExamData) {
        //自分の解答を登録
        examData.answered_list.add(choice_number)
        //正解をDBから取得
        val answers = SQLiteHelper(this)
        val db = answers.readableDatabase
        val AOH = AnswersOpenHelper(db)
        var answer = AOH.find_answers(examData.question_current)?.get(1) //正しい正解
        var isCorrected = false //正解だった場合にtrueにする
        if (choice_number == answer) {
            isCorrected = true
        }
        examData.isCorrect_list.add(isCorrected)//解いた問題が正解だったかどうかがBoolean型で入る
    }
}