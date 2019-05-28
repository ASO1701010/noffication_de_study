package jp.ac.asojuku.st.noffication_de_study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ScreenTransition {
    override fun title_questionOption(user_id: Int) {
        super.title_questionOption(user_id)
        val intent = Intent(this, OptionActivity::class.java)
        intent.putExtra("user_id", user_id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
