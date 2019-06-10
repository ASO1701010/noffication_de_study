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
        Log.d("123", find_last_update())

        ApiGetTask {
            //            itの値チェック用ですが、itのデータが大きすぎて全ての表示ができません。
//            jsonArrayのキー値を設定して、個別に確認してください。
//            Log.d("test",JSONObject(it).getJSONObject("data").getJSONArray("questions_db").toString())
            all_update(JSONObject(it))
        }.execute("db-update.php")
    }

    //    最終アップデートの日付を、yyyy-MM-dd のフォーマットでStringとして返す。
    fun find_last_update(): String {
        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        val query = "SELECT update_date FROM questions  ORDER BY update_date desc limit 1"
        var cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        var result = cursor.getString(0).toString()
        cursor.close()
        db.close()
        return result
    }

    //    受け取った全ての値をDBに登録する。
    fun all_update(callback: JSONObject): Boolean {
        var json = callback
        Log.d("TEST", "Nya-n1")
        if (json.getString("status") != "S00") {
            Log.d("TEST", json.toString())
            return false
        }
        Log.d("TEST", "Nya-n2")
        json = json.getJSONObject("data")

        val db = SQLiteHelper(this).writableDatabase

        val answers = AnswersOpenHelper(db)
        val answers_rate = AnswersRateOpenHelper(db)
        val correct_answer = CorrectAnswerOpenHelper(db)
        val exams_numbers = ExamsNumbersOpenHelper(db)
        val exams_questions = ExamsQuestionsOpenHelper(db)
        val genres = GenresOpenHelper(db)
        val image = ImageOpenHelper(db)
        val questions = QuestionsOpenHelper(db)
        val questions_genres = QuestionsGenresOpenHelper(db)
//        val user_answers = UserAnswersOpenHelper(ac)


        var jArray = json.getJSONArray("answer_db")
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                answers.add_record(
                    jArray.getInt(0),
                    jArray.getInt(1)
                )
            }
        }
        jArray = json.getJSONArray("answers_rate_db")
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                answers_rate.add_record(
                    jArray.getInt(0),
                    jArray.getDouble(1)
                )
            }
        }
        jArray = json.getJSONArray("exams_numbers_db")
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                exams_numbers.add_record(
                    jArray.getJSONObject(i).getInt("exam_id")
                    , jArray.getJSONObject(i).getString("exams_number")
                )
            }
        }
        jArray = json.getJSONArray("exams_questions_db")
        if (jArray != {}) {
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
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                genres.add_record(
                    jArray.getJSONObject(i).getInt("genre_id"),
                    jArray.getJSONObject(i).getString("genre_name")
                )
            }
        }
        jArray = json.getJSONArray("image_db")
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                image.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getString("file_name")
                )
            }
        }
        jArray = json.getJSONArray("questions_db")
        if (jArray != {}) {
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
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                questions_genres.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getInt("genre_id")
                )
            }
        }
        jArray = json.getJSONArray("correct_answer_db")
        if (jArray != {}) {
            for (i in 0..jArray.length() - 1) {
                correct_answer.add_record(
                    jArray.getJSONObject(i).getInt("question_id"),
                    jArray.getJSONObject(i).getInt("correct_answer_number")
                )
            }
        }
//        テーブル内データ確認用
//        Log.d("tete1",answers.find_answers(1).toString())
//        Log.d("tete2",answers_rate.find_all_rate().toString())
//        Log.d("tete3",exams_numbers.find_exams_numbers(1).toString())
//        Log.d("tete4",exams_questions.find_all_questions(1,"FE2019S").toString())
//        Log.d("tete5",genres.find_genre(1).toString())
//        Log.d("tete6",image.find_image(1).toString())
//        Log.d("tete7",questions.find_question(1).toString())
//        Log.d("tete8",questions.find_comment(1).toString())
//        Log.d("tete9",questions_genres.find_question_genres(1).toString())
//        Log.d("tete0",questions_genres.find_genre_questions(1).toString())
//        Log.d("tete10",correct_answer.find_correct_answer(1).toString())
        db.close()
        return true
    }

    fun get_user_id(token: String) {

    }
}
