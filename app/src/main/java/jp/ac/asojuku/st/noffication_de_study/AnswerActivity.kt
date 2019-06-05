package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_question.*
import org.jetbrains.anko.startActivity

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        AA_End_BTN.setOnClickListener {
            startActivity<TitleActivity>()
        }

        AA_Next_BTN.setOnClickListener {
            startActivity<QuestionOptionActivity>()
        }
    }
}