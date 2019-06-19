package jp.ac.asojuku.st.noffication_de_study

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_option.*
import android.content.SharedPreferences
import android.R.id.edit
import android.app.TimePickerDialog
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager
import android.content.Intent;
import android.preference.PreferenceDataStore
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.View.OnClickListener;
import android.widget.*
import android.support.v4.app.DialogFragment;


//TODO オプション画面:未完成(0%)
class OptionActivity : AppCompatActivity(),TimePickerFragment.OnTimeSelectedListener,TimePickerFragment2.OnTimeSelectedListener {
    override fun onSelected(hourOfDay: Int, minute: Int) {
        OA_Noffication_Time_Between1.text = "%1&02d:%2&02d".format(hourOfDay,minute) //timepickerによってtextviewの変更(未確認)
        OA_Noffication_Time_Between2.text = "%1&02d:%2&02d".format(hourOfDay,minute) //同上
    }


    //TODO 定数の中身はすべて仮の値
    val user_id = 12345678 //ユーザID
    val user_auth = "USER_AUTH" //自動でログインする用の変数
    val rule_num: Int = 123 //次に追加するruleのid
    val checkNDS = OA_NDS_Mode_BTN.isChecked()
    val checkSDS = OA_SDS_Mode_BTN.isChecked()
    val Spinner = OA_Noffication_Interval.getSelectedItemPosition()
    val dialog = TimePickerFragment()
    val dialog2 = TimePickerFragment2()
    val option : SharedPreferences.Editor = getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE).edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        OA_Google_Login_BTN.setOnClickListener { login() } //googleログイン
        OA_NDS_Mode_BTN.setOnClickListener { regRule() }
        OA_SDS_Mode_BTN.setOnClickListener { regRule() }
        OA_Noffication_Time_Between1.setOnClickListener {
            dialog.show(supportFragmentManager,"StartTime_dialog")
            regRule()
            }
        OA_Noffication_Time_Between2.setOnClickListener {
            dialog2.show(supportFragmentManager,"EndTime_dialog")
            regRule()
            }
        OA_Noffication_Interval.setOnClickListener { regRule() }
    }

    //Googleログインを行うメソッド
    fun login() {

    }

    //ルール登録
    fun regRule() {
        if(checkNDS){ //通知de勉強ボタンのチェック状態保存
            option.putBoolean("NDS_check",true).apply()
        }else{
            option.putBoolean("NDS_check",false).apply()
        }
        if(checkSDS){ //画面点灯de勉強ボタンのチェック状態保存
            option.putBoolean("SDS_check",true).apply()
        }else{
            option.putBoolean("SDS_check",false).apply()
        }
        option.putString("NDS_Start", dialog.toString()).apply()
        option.putString("NDS_End",dialog2.toString()).apply()
        option.putInt("NDS_Interval",Spinner).apply() //通知間隔設定の保存
    }
}
