package jp.ac.asojuku.st.noffication_de_study

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

//TODO オプション画面のアクティビティ:未完成(0%)
class OptionActivity : AppCompatActivity() {

    //TODO 定数の中身はすべて仮の値
    val user_id = 12345678 //ユーザID
    val user_auth = "USER_AUTH" //自動でログインする用の変数
    val rule_num:Int = 123 //次に追加するruleのid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
    }

    //Googleログインを行うメソッド
    fun login() {

    }

    //ルール登録
    fun regRule() {

    }

    //ルール追加
    fun addRule() {

    }
}
