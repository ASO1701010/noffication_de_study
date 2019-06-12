package jp.ac.asojuku.st.noffication_de_study

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question.*
import org.json.JSONObject
import java.util.*

class QuestionActivity: AppCompatActivity() {

    var exam_data = ExamData(0,"","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        exam_data = intent.getSerializableExtra("exam_data") as ExamData


        AA_Answer_0.setOnClickListener{

        }
    }

    fun regResult(){
        val date: Date = Date()
        val test1: String = DateFormat.format("yyyy-MM-dd", date).toString()
//        Log.d("tetes",hashMapOf(
//            "question_id" to exam_data.question_current,
//            "answer_choice" to exam_data.answered_list.get(exam_data.question_current),
//            "answer_time" to test1).toString())
        ApiPostTask {
            Log.d("tetes",it)
            if(JSONObject(it).getString("status") != "E00"){
                Toast.makeText(this, "APIの通信に成功しました(｀・ω・´)", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "APIの通信に失敗しました(´･ω･`)", Toast.LENGTH_SHORT).show()
            }
        }.execute("add-answer.php", hashMapOf(
                "question_id" to exam_data.question_current,
                "answer_choice" to exam_data.answered_list.get(exam_data.question_current),
                "answer_time" to test1).toString()
        )
    }
}

