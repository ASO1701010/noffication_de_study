package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jp.ac.asojuku.st.noffication_de_study.db.AnswersOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.CorrectAnswerOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.ExamsNumbersOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import kotlinx.android.synthetic.main.activity_answer.*
import org.jetbrains.anko.startActivity

class AnswerActivity : AppCompatActivity() {
//    val questions = SQLiteHelper(this)
//    val db = questions.readableDatabase
//    val questionsDB = QuestionsOpenHelper(db)
//    val correctDB = CorrectAnswerOpenHelper(db)


    lateinit var user_id:String
    lateinit var exam_data:ExamData

    var question_id = -1
    var answer_num:Int? = 999999
    var answer_text:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        user_id = getSharedPreferences("user_data", MODE_PRIVATE).getString("user_id","999999")
        exam_data = intent.getSerializableExtra("exam_data") as ExamData

        at_first()





        AA_Back_BTN.setOnClickListener {
            var data = ArrayList<String>()
            startActivity<TitleActivity>("exam_data" to exam_data)
        }

        AA_Next_BTN.setOnClickListener {

            startActivity<QuestionActivity>("exam_data" to exam_data)
        }


    }
    fun at_first(){

        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        val questionsDB = QuestionsOpenHelper(db)
        val correctDB = CorrectAnswerOpenHelper(db)

        if(user_id == "999999"){
            Log.d("user_id が受け取れていません","/")
        }
        answer_text = questionsDB.find_comment(question_id)?.get(1)
        answer_num = correctDB.find_correct_answer(question_id)
        print_answer()
    }

    //テスト用データが入ってます
    //本番用は上のコメントアウトされてる部分です。
    fun print_answer(){
        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        val questionsDB = QuestionsOpenHelper(db)

        question_id = exam_data.question_current


        var examNumberList:ArrayList<String>?
        ///////問題IDから試験回を取得///////DB側未作成////////////////////////////////////
        val examsNumbersDB = ExamsNumbersOpenHelper(db)
        examNumberList = examsNumbersDB.find_exams_numbers(question_id)
        ////////////////////////////////////////////////////////////////////////
        //正解を取得//
        val answersDB = AnswersOpenHelper(db)
        var answer = answersDB.find_answers(question_id)
        //////////////

        //改行コードの取得//
        val BR:String = System.getProperty("line.separator")
        var examNumbers:String = ""
        for (i in 0..examNumberList!!.size-1){
            examNumbers += examNumberList[i] + BR
        }

        answer_examNumber_text.setText(exam_data.number)
        answer_questionNumber_text.setText("問題ID："+ question_id)
        answer_question_correct_text.setText("正解 : " + answer)
        AA_answerComment_text.setText(questionsDB.find_comment(question_id)?.get(1))
//        answer_examNumber_text.setText("問題number")
//        answer_questionNumber_text.setText("問 1")
//        answer_question_correct_text.setText("正解 : あ" )
//        answer_answerText_text.setText("カクカク")
    }
}