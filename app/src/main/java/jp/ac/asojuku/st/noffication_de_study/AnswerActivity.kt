package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jp.ac.asojuku.st.noffication_de_study.db.CorrectAnswerOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import kotlinx.android.synthetic.main.activity_answer.*
import org.jetbrains.anko.startActivity

class AnswerActivity : AppCompatActivity() {
//    val questionsDB: QuestionsOpenHelper = QuestionsOpenHelper(this)
//    val correctDB: CorrectAnswerOpenHelper = CorrectAnswerOpenHelper(this)

    val user_id:Int = getSharedPreferences("user_data", MODE_PRIVATE).getInt("user_id",999999)
    val exam_data:ExamData = intent.getSerializableExtra("exam_data") as ExamData
    val question_id = exam_data.question_current
    var answer_num:Int? = 999999
    var answer_text:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        AA_Back_BTN.setOnClickListener {
            var data = ArrayList<String>()
            startActivity<TitleActivity>("exam_data" to exam_data)
        }

        AA_Next_BTN.setOnClickListener {

            startActivity<QuestionActivity>("exam_data" to exam_data)
        }


    }
    fun at_first(){
        if(user_id == 999999){
            Log.d("user_id が受け取れていません","/")
        }
//        answer_text = questionsDB.find_comment(question_id)?.get(1)
//        answer_num = correctDB.find_correct_answer(question_id)
        print_answer()
    }

    //テスト用データが入ってます
    //本番用は上のコメントアウトされてる部分です。
    fun print_answer(){
//        answer_examNumber_text.setText(exam_data.exams_number)
//        answer_questionNumber_text.setText("問 "+ exam_data.exams_number)
//        answer_question_correct_text.setText("正解 : " + exam_data)
//        answer_answerText_text.setText(questionsDB.find_comment(question_id)?.get(1))
//        answer_examNumber_text.setText("問題number")
//        answer_questionNumber_text.setText("問 1")
//        answer_question_correct_text.setText("正解 : あ" )
//        answer_answerText_text.setText("カクカク")
    }
}