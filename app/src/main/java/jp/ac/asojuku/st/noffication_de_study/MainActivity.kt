package jp.ac.asojuku.st.noffication_de_study


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DB = UserAnswersOpenHelper(this)  // DB生成
        val db = DB.readableDatabase

//        db.delete("user_answers", null, null);
        DB.add_record(1,1,1,1111,db)
        DB.add_record(2,1,2,2222,db)
        DB.add_record(3,2,1,3333,db)
        DB.add_record(4,3,1,4444,db)
        DB.add_record(5,4,2,5555,db)



        button.setOnClickListener {

            Log.d("1:",DB.find_all_user_answers().toString())
            Log.d("1:",DB.find_user_answers(1).toString())
            Log.d("1:",DB.find_user_answers(2).toString())
            Log.d("1:",DB.find_user_answers(3).toString())
            Log.d("1:",DB.find_user_answers(4).toString())
            Log.d("1:",DB.find_user_answers(5).toString())









        }

    }

}
