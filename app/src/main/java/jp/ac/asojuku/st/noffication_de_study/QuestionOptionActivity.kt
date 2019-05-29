package jp.ac.asojuku.st.noffication_de_study

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

//TODO 問題オプション画面：未完成（0%）
class QuestionOptionActivity : AppCompatActivity() {

    //TODO 定数の値はすべて仮の値
    val user_id = 12345678
//    var question_list 中身が無いと宣言できない
//    var setting_list 中身が無いと宣言できない

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_option)

    }

    //選択肢を読み込む
    fun loadChoice() {

    }

    //読み込んだ選択肢を各セレクトボックスやスピナーにセットする
    //object型を引数には設定できない
//    fun setChoise(target_list : object) {
//
//    }

    //設定完了時の処理。出題する問題を決定し配列に格納、画面遷移を行う
    fun decideSetting() {

    }

    //選択設定判定
    //設定されたチェックをすべて検索しsetting_listに登録
    fun discChoice() {

    }

    //出題問題決定
    //出題する問題を決定し問題IDを配列で返す。未設定時はランダムに20問
    //TODO このメソッドは戻り値にArray<Int>を戻り値にする？
    fun decideQuestion() {

    }

}
