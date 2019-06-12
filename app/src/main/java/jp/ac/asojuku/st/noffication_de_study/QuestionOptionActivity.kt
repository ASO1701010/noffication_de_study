package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.ac.asojuku.st.noffication_de_study.db.ExamsQuestionsOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsGenresOpenHelper
import kotlinx.android.synthetic.main.activity_question_option.*
import org.jetbrains.anko.startActivity

//TODO 問題オプション画面：未完成（30%）
class QuestionOptionActivity : AppCompatActivity() {

    //TODO 定数の値はすべて仮の値
    val user_id = 12345678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_option)

        QOA_Start_BTN.setOnClickListener {
            // 開始ボタンが押された際に、選択を読みこむ
//            loadChoice()

            startActivity<QuestionActivity>()
        }

        QOA_Back_BTN.setOnClickListener {
            finish()
        }
    }

    //選択肢を読み込む
    fun loadChoice() {
        // ランダム出題するかどうか
        // ランダム出題の場合、randomBooleanの中身がtrueに
        val randomBoolean = QOA_Select_Method_Random_RBTN.isChecked

        // 問題数スピナーから問題数を取得する
        val SpinnerStr = QOA_Question_Amount_BOX.getSelectedItem()
        var SpinnerNum = 5

        // スピナーの文字列に合わせて
        if (SpinnerStr == "5問") {
            SpinnerNum = 5
        } else if (SpinnerStr == "10問") {
            SpinnerNum = 10
        } else if (SpinnerStr == "15問") {
            SpinnerNum = 15
        } else if (SpinnerStr == "20問") {
            SpinnerNum = 20
        } else if (SpinnerStr == "25問") {
            SpinnerNum = 25
        } else if (SpinnerStr == "30問") {
            SpinnerNum = 30
        } else if (SpinnerStr == "35問") {
            SpinnerNum = 35
        } else if (SpinnerStr == "40問") {
            SpinnerNum = 40
        } else if (SpinnerStr == "45問") {
            SpinnerNum = 45
        } else if (SpinnerStr == "50問") {
            SpinnerNum = 50
        }

        // 問題DBの生成
        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase

        // 数値はすべて仮数
        // 出題年度ごとの問題の読み込み
        val EQOH = ExamsQuestionsOpenHelper(db)
        var TempYear: ArrayList<ArrayList<Int>>?
        lateinit var TempYear_list: ArrayList<ArrayList<ArrayList<Int>>?>

        // 試験回選択
        // 出題IDはFEだけなので、exams_idは1?
        if (QOA_Select_Exam_Number_H31S_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2019S")
            TempYear_list.add(TempYear)
        }
        if (QOA_Select_Exam_Number_H30F_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2018F")
            TempYear_list.add(TempYear)
        }
        if (QOA_Select_Exam_Number_H30S_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2018S")
            TempYear_list.add(TempYear)
        }
        if (QOA_Select_Exam_Number_H29F_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2017F")
            TempYear_list.add(TempYear)
        }
        if (QOA_Select_Exam_Number_H29S_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2017S")
            TempYear_list.add(TempYear)
        }
        if (QOA_Select_Exam_Number_H28F_RBTN.isChecked) {
            TempYear = EQOH.find_all_questions(1, "FE2016F")
            TempYear_list.add(TempYear)
        }


        // ジャンルの読み込み
        val GOH = QuestionsGenresOpenHelper(db)
        var genre1_Questions: ArrayList<Int>? = null
        // ジャンル検索
        if (QOA_Select_Genres_1.isChecked) {
            genre1_Questions = GOH.find_genre_questions(1)
        }
        if (QOA_Select_Genres_2.isChecked) {
            val genre2_Questions = GOH.find_genre_questions(2)
        }

        // 取得した問題から全てのArrayListに存在するものを書き出す
        // ArrayListのTempQuestionsに出題する問題を格納する
        var TempQuestions: ArrayList<Int>?

//        TempQuestions = genre1_Questions.filter

        if (genre1_Questions != null) {
            for (ty in TempYear_list) {
                for (i in 0..ty!!.size) {
                    for (j in 1..genre1_Questions.size) {

                        if (genre1_Questions.get(i) == ty.get(j).get(2)){

                    }
                }
            }
        }
    }
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
