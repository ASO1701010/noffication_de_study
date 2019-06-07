package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
//import android.util.Log
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val task1 = APConnectionManager()
        task1.get("db-update.php",hashMapOf("last_update_date" to "2019-05-26")).doAsync { Log.d("test",task1.json.toString()) }
    }
}
