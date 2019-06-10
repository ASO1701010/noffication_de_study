package jp.ac.asojuku.st.noffication_de_study

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import kotlinx.android.synthetic.main.activity_question.*
import java.lang.Exception

class QuestionActivity : AppCompatActivity() {

    var user_id: Int = 12345678 //テスト用
    var examData: ExamData = intent.getSerializableExtra("ExamData") as ExamData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        atFirst()
    }

    //コンストラクタ
    fun atFirst() {
        //集計か出題かを判定？
    }

    //次の問題設定
    fun choiceNextQuestion() {
        examData.question_current = examData.question_next
        try {
            //次の問題が存在するなら次の問題番号を入れる
            examData.question_list.get(examData.question_next + 1)
            examData.question_next++

        } catch (e:ArrayIndexOutOfBoundsException) {
            //次の問題が存在しない場合は次に999を設定する
            examData.question_next = 999
        }


    }

    //問題表示
    fun printQuestion() {
        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        val QOH = QuestionsOpenHelper(db)
        var question_arr:ArrayList<String>? = QOH.find_question(examData.question_current)
        var question_str:String
        if (question_arr == null) {
            question_str = "問題文がありません"
        } else {
            question_str = question_arr[0]
        }

        textView4.setText(question_str)

    }

    //解答選択
    fun choiceAnswer(question_number: Int) {

    }

    //スキップ
    fun skipQuestion() {

    }

    //解答登録
    fun regAnswer(question_number: Int) {

    }

    //結果集計
    fun collectResult() {

    }

    //結果登録
    fun regResult() {

    }

    //結果表示
    fun printResult() {

    }

}

