package jp.ac.asojuku.st.noffication_de_study

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_option.*
import android.content.SharedPreferences
import android.R.id.edit
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager
import android.content.Intent;
import android.preference.PreferenceDataStore
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;


//TODO オプション画面:未完成(0%)
class OptionActivity : AppCompatActivity() {

    //TODO 定数の中身はすべて仮の値
    val user_id = 12345678 //ユーザID
    val user_auth = "USER_AUTH" //自動でログインする用の変数
    val rule_num: Int = 123 //次に追加するruleのid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        val dataStore: SharedPreferences = getSharedPreferences("DataStore",Context.MODE_PRIVATE)

        OA_Google_Login_BTN.setOnClickListener { login() } //googleログイン
        OA_NDS_Mode_BTN.setOnClickListener {
            val check = OA_NDS_Mode_BTN.isChecked()
            if(check){
                regRule()
            }else{

            }
        }
        OA_SDS_Mode_BTN.setOnClickListener {
            val check = OA_SDS_Mode_BTN.isChecked()
            if(check){
                regRule()
            }else{

            }
        }
        OA_Noffication_Time_Between.setOnClickListener {  }
        OA_Noffication_Interval.setOnClickListener {  }
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
