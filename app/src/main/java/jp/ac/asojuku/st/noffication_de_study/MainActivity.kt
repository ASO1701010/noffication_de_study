package jp.ac.asojuku.st.noffication_de_study

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jp.ac.asojuku.st.noffication_de_study.db.*
//import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiGetTask{
            Log.d("test",it.toString())
            all_update(Date(),JSONObject(it))
        }.execute("db-update.php", hashMapOf("last_update_date" to "2019-05-26").toString())
    }

    fun all_update(yyyyMMdd: Date,callback:JSONObject):Boolean{
        var format = SimpleDateFormat("yyyy-mm-dd")
//        System.out.println(format.format(Date()));
//        val data = get("db-update.php", hashMapOf("last_update_date" to format.format(yyyyMMdd)))
        var json = callback
        if(json.getString("status")!="S00"){
            return false
        }
        json = json.getJSONObject("data")

        val answers = AnswersOpenHelper(this)
        val answers_rate = AnswersRateOpenHelper(this)
        val correct_answer = CorrectAnswerOpenHelper(this)
        val exams_numbers = ExamsNumbersOpenHelper(this)
        val exams_questions = ExamsQuestionsOpenHelper(this)
        val genres = GenresOpenHelper(this)
        val image = ImageOpenHelper(this)
        val questions = QuestionsOpenHelper(this)
        val questions_genres = QuestionsGenresOpenHelper(this)
//        val user_answers = UserAnswersOpenHelper(ac)


        var jArray = json.getJSONArray("answer_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                answers.add_record(
                    jArray.getInt(0),
                    jArray.getInt(1)
                )
            }
        }
        jArray = json.getJSONArray("answers_rate_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                answers_rate.add_record(
                    jArray.getInt(0),
                    jArray.getDouble(1)
                )
            }
        }
        jArray = json.getJSONArray("exams_numbers_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                exams_numbers.add_record(
                    jArray.getJSONObject(i).getInt("exam_id")
                    ,jArray.getJSONObject(i).getString("exams_number")
                )
            }
        }
        jArray = json.getJSONArray("exams_questions_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                exams_questions.add_record(
                    jArray.getJSONObject(i).getInt("exam_id"),
                    jArray.getJSONObject(i).getString("exams_number"),
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getInt("question_number")
                )
            }
        }
        jArray = json.getJSONArray("genres_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                genres.add_record(
                    jArray.getJSONObject(i).getInt("genre_id"),
                    jArray.getJSONObject(i).getString("genre_name")
                )
            }
        }
        jArray = json.getJSONArray("image_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                image.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getString("file_name")
                )
            }
        }
        jArray = json.getJSONArray("questions_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                questions.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getString("question"),
                    jArray.getJSONObject(i).getInt("is_have_image"),
                    jArray.getJSONObject(i).getString("comment"),
                    jArray.getJSONObject(i).getString("update_date")
                )
            }
        }
        jArray = json.getJSONArray("questions_genres_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                questions_genres.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getInt("genre_id")
                )
            }
        }
        jArray = json.getJSONArray("correct_answer_db")
        if(jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                correct_answer.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getInt("correct_answer_number")
                )
            }
        }




        Log.d("tete1",answers.find_answers(1).toString())
        Log.d("tete2",answers_rate.find_all_rate().toString())
        Log.d("tete3",exams_numbers.find_exams_numbers(1).toString())
        Log.d("tete4",exams_questions.find_all_questions(1,"FE2019S").toString())
        Log.d("tete5",genres.find_genre(1).toString())
        Log.d("tete6",image.find_image(1).toString())
        Log.d("tete7",questions.find_question(1).toString())
        Log.d("tete8",questions.find_comment(1).toString())
        Log.d("tete9",questions_genres.find_question_genres(1).toString())
        Log.d("tete0",questions_genres.find_genre_questions(1).toString())
        Log.d("tete10",correct_answer.find_correct_answer(1).toString())

        return true
    }


    fun get_user_id(token:String){

    }
}
