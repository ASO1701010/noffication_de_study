package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.ac.asojuku.st.noffication_de_study.db.ExamsQuestionsOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsGenresOpenHelper
import kotlinx.android.synthetic.main.activity_question_option.*
import org.jetbrains.anko.startActivity

//TODO 問題オプション画面：未完成（0%）
class QuestionOptionActivity : AppCompatActivity() {

    //TODO 定数の値はすべて仮の値
    val user_id = 12345678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_option)

        QOA_Start_BTN.setOnClickListener {
            // 開始ボタンが押された際に、選択を読みこむ
//            loadChoice()

            startActivity<AnswerActivity>()
        }

        QOA_Back_BTN.setOnClickListener {
            startActivity<TitleActivity>()
        }
    }

    //選択肢を読み込む
    fun loadChoice() {
        // ランダム出題するかどうか
        // ランダム出題の場合、randomBooleanの中身がtrueに
        val randomBoolean = QOA_Select_Method_Random_RBTN.isChecked

        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        // 数値はすべて仮数

        // 出題年度ごとの問題の読み込み
        val EQOH = ExamsQuestionsOpenHelper(db)
        var TempYear_list = ArrayList<ArrayList<ArrayList<Int>>?>()
        // 問題検索
        // 出題IDはFEだけなので1?
        if (QOA_Select_Exam_Number_H31S_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2019S"))
        }
        if (QOA_Select_Exam_Number_H30F_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2018F"))
        }
        if (QOA_Select_Exam_Number_H30S_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2018S"))
        }
        if (QOA_Select_Exam_Number_H29F_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2017F"))
        }
        if (QOA_Select_Exam_Number_H29S_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2017S"))
        }
        if (QOA_Select_Exam_Number_H28F_RBTN.isChecked) {
            TempYear_list.add(EQOH.find_all_questions(1, "FE2016F"))
        }


        // ジャンルの読み込み
        val GOH = QuestionsGenresOpenHelper(db)
        // ジャンル検索
        if (QOA_Select_Genres_1.isChecked) {
            val genre1_Questions = GOH.find_genre_questions(1)
        }
        if (QOA_Select_Genres_2.isChecked) {
            val genre2_Questions = GOH.find_genre_questions(2)
        }


    }

    //読み込んだ選択肢を各セレクトボックスやスピナーにセットする
    //object型を引数には設定できない
//    fun setChoise(target_list : object) {
//
//    }

    //設定完了時の処理。出題する問題を決定し配列に格納、画面遷移を行う
    fun decideSetting() {

    }


    //出題問題決定
    //出題する問題を決定し問題IDを配列で返す。未設定時はランダムに20問
    //TODO このメソッドは戻り値にArray<Int>を戻り値にする？
    fun decideQuestion() {

    }

}
