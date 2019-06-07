package jp.ac.asojuku.st.noffication_de_study
//
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import java.util.*

class APConnectionManager(var ac:AppCompatActivity) {
    val server = "http://eve.main.jp/noffication-de-study/api/"
    var json = JSONObject()

    fun all_update(yyyyMMdd: Date,ac:AppCompatActivity):Boolean{
        var format = SimpleDateFormat("yyyy-mm-dd")
//        System.out.println(format.format(Date()));
        val data = get("db-update.php", hashMapOf("last_update_date" to format.format(yyyyMMdd)))
        if(json.getString("status")!="S00"){
            return false
        }
        json = json.getJSONObject("data")

        val answers = AnswersOpenHelper(ac)
        val answers_rate = AnswersRateOpenHelper(ac)
        val correct_answer = CorrectAnswerOpenHelper(ac)
        val exams_numbers = ExamsNumbersOpenHelper(ac)
        val exams_questions = ExamsQuestionsOpenHelper(ac)
        val genres = GenresOpenHelper(ac)
        val image = ImageOpenHelper(ac)
        val questions = QuestionsOpenHelper(ac)
        val questions_genres = QuestionsGenresOpenHelper(ac)
//        val user_answers = UserAnswersOpenHelper(ac)


        var jArray = json.getJSONArray("answers_db")
        if(jArray != {}) {
            for (i in 0..json.getJSONArray("answers_rate_db").length() - 1) {
                answers.add_record(jArray.getInt(0),jArray.getInt(1))
            }
        }

        jArray = json.getJSONArray("answers_rate_db")
        if(jArray != {}) {
            for (i in 0..json.getJSONArray("answers_rate_db").length() - 1) {
                answers_rate.add_record(jArray.getInt(0),jArray.getInt(1))
            }
        }

        setColmun(answers_rate,json.getJSONArray("answer_db"))
        setColmun(correct_answer,json.getJSONArray("correct_answer_db"))
        setColmun(exams_numbers,json.getJSONArray("exams_numbers_db"))
        setColmun(exams_questions,json.getJSONArray("exams_questions_db"))
        setColmun(genres,json.getJSONArray("genres_db"))
        setColmun(image,json.getJSONArray("image_db"))
        setColmun(questions,json.getJSONArray("questions_db"))
        setColmun(questions_genres,json.getJSONArray("questions_genres_db"))


        return true
    }


    fun get_user_id(token:String){

    }

    fun get(api_name: String, param: HashMap<String, String>) {
        /*
        val api = server + api_name
        val urlBuilder = HttpUrl.parse(api)!!.newBuilder()

        param.forEach { name, value -> urlBuilder.addQueryParameter(name, value) }
        ApiGetTask(urlBuilder.build()).execute()
        */
        ApiGetTask(api_name, param).execute()
    }

    fun post(api_name: String, param: HashMap<String, String>) {
        /*
        val api = server + api_name

        val formBuilder = FormBody.Builder()
        params.forEach { name, value -> formBuilder.add(name, value) }
        val requestBody = formBuilder.build()
        ApiPostTask(api, requestBody).execute()
        */
        ApiPostTask(api_name, param).execute()
    }

    inner class ApiGetTask(private val api_name: String, private val param: HashMap<String, String>) : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val api = server + api_name
            val urlBuilder = HttpUrl.parse(api)!!.newBuilder()

            param.forEach { name, value -> urlBuilder.addQueryParameter(name, value) }

            val request = Request.Builder().url(urlBuilder.toString()).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            val data = response.body()!!.string()
            Log.d("TEST", data)
            json = JSONObject(data)
            return data
        }
    }

    inner class ApiPostTask(private val api_name: String, private val param: HashMap<String, String>) : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val api = server + api_name

            val formBuilder = FormBody.Builder()
            param.forEach { name, value -> formBuilder.add(name, value) }
            val body = formBuilder.build()

            val request = Request.Builder().url(api).post(body).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            val data = response.body()!!.string()
            Log.d("TEST", data)
            return data
        }
    }

}
