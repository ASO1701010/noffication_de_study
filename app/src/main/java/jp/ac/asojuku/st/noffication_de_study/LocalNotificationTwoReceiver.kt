package jp.ac.asojuku.st.noffication_de_study

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class LocalNotificationTwoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val questionId = intent.getIntExtra("question_id", 0)
        val userAnswer = intent.getIntExtra("user_answer", 0)
        if (questionId == 0 || userAnswer == 0) {
            return Toast.makeText(context, "採点できませんでした", Toast.LENGTH_LONG).show()
        }

        val helper = SQLiteHelper(context)
        val db = helper.readableDatabase
        val query = "SELECT * FROM answers WHERE question_id = ?"
        val cursor = db.rawQuery(query, arrayOf(questionId.toString()))
        var questionAnswer: Int = 0
        cursor.use { c ->
            c.moveToFirst()
            for (i in 0 until c.count) {
                questionAnswer = c.getInt(c.getColumnIndex("answer_number"))
                c.moveToNext()
            }
        }
        if (questionAnswer == 0) {
            return Toast.makeText(context, "採点できませんでした", Toast.LENGTH_LONG).show()
        }
        if (userAnswer == questionAnswer) {
            Toast.makeText(context, "正解です！", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "外れです！", Toast.LENGTH_LONG).show()
        }
    }

}