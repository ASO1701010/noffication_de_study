package jp.ac.asojuku.st.noffication_de_study

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.startActivity

//TODO 統計情報画面:未完成(0%)
class StaticsActivity : AppCompatActivity() {

    //定数はすべて仮の値
    val user_id:Int = 12345678 //ユーザID
    var my_record:String = "testRecord" //my記録
    var correct_rate:String = "999%" //正答率
    var text_print:String = "testTextPrint" //表示に利用する

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statics)

        SA_Back_BTN.setOnClickListener {
            startActivity<TitleActivity>()
        }

        SA_My_Statics_BTN.setOnClickListener {

        }

        SA_Question_Statics_BTN.setOnClickListener {

        }
    }

    //my記録表示
    fun printMyRecord() {

    }

    //問題正答率表示
    fun printCorrectRate() {

    }

    //表示切り替え
    fun changeTab() {

    }

    //表示文字列生成
    fun createStrToPrint() {

    }

}
