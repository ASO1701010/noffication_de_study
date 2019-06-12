package jp.ac.asojuku.st.noffication_de_study

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_statics.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject

//TODO 統計情報画面:未完成(20%)
class StaticsActivity : AppCompatActivity() {

    //定数はすべて仮の値
    val user_id:Int = 12345678 //ユーザID
    var my_record:String = "testRecord" //my記録
    var correct_rate:String = "999%" //正答率
    var text_print:String = "testTextPrint" //表示に利用する

    var jsondata = JSONObject() // get_statics() の戻り値を格納。 統計情報が入っております。

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statics)

        SA_Back_BTN.setOnClickListener {
            finish()
        }

        SA_My_Statics_BTN.setOnClickListener {

        }

        SA_Question_Statics_BTN.setOnClickListener {
            get_statics()
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

    //統計情報取得
    fun get_statics(){
        var result_flg = true
        var json = JSONObject()
        ApiGetTask {
            if (!it.isNullOrEmpty()) {
                Log.d("it",it)
                json = JSONObject(it)

            } else {
                Toast.makeText(this, "APIの通信に失敗しました(´･ω･`)", Toast.LENGTH_SHORT).show()
                result_flg = false
            }
        }.execute("get-statistics-info.php")

        if(result_flg==true){
            jsondata = json
        }
    }
}
