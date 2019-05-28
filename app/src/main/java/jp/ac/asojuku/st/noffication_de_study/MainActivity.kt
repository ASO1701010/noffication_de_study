package jp.ac.asojuku.st.noffication_de_study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ScreenTransition {
    override fun title_questionOption() {
        super.title_questionOption()
        val intent = Intent(this, OptionActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
