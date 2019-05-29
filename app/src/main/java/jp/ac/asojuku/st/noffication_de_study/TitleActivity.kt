package jp.ac.asojuku.st.noffication_de_study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_title.*


class TitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        //TODO questionUpdateQuery()引数の最終更新日
        //var isUpdate = questionUpdateQuery()
        var isAuthOK = autoAuthentication()
        //TODO autoAuthentication()の戻り値による処理の変化


        //TA_Question_BTN.setOnClickListener{ ontitle_questionOption(intent)} //「問題を解く」を押したときのメソッド呼び出し
        //TA_Statics_BTN.setOnClickListener { ontitle_statics(intent) }   //「統計情報」を押したときのメソッド呼び出し
        //TA_Option_BTN.setOnClickListener { ontitle_option(intent) }     //「オプション」を押したときのメソッド呼び出し
    }

//    fun questionUpdateQuery(:Int):Boolean{ //引数に日付を入力
//        //「更新問い合わせ」
//        //TODO questionUpdateQuery()の処理内容
//
//        if (){
//            questionUpdateExcute()
//            return true
//        }else{
//            return false
//        }
//    }
    fun questionUpdateExcute(){
        //問題更新
        //TODO questionUpdateExcute()の処理内容
    }
    fun autoAuthentication():Boolean{
        //認証
        //TODO autoAuthentication()の処理内容
        return true //仮戻り値
    }
}
