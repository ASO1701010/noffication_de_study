package jp.ac.asojuku.st.noffication_de_study

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class APConnectionManager(activity: AppCompatActivity) {
    val server = "http://eve.main.jp/noffication-de-study/api/"

    fun get(api_name: String, param: HashMap<String, String>) {
        val api = server + api_name
        val urlBuilder = HttpUrl.parse(api)!!.newBuilder()

        param.forEach { name, value -> urlBuilder.addQueryParameter(name, value) }
        ApiGetTask(urlBuilder.build()).execute()
    }

    fun post(api_name: String, params: HashMap<String, String>) {
        val api = server + api_name

        val formBuilder = FormBody.Builder()
        params.forEach { name, value -> formBuilder.add(name, value) }
        val requestBody = formBuilder.build()
        ApiPostTask(api, requestBody).execute()
    }

    inner class ApiGetTask(private val url: HttpUrl) : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val request = Request.Builder().url(url).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            val data = response.body()!!.string()
            Log.d("TEST", data)
            return false.toString()
        }
    }

    inner class ApiPostTask(private val url: String, private val body: FormBody) : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val request = Request.Builder().url(url).post(body).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            val data = response.body()!!.string()
            Log.d("TEST", data)
            return false.toString()
        }
    }

    /*
    //TODO apiへ問い合わせを行いJSONObjectを返す
    fun apiRequest(URL:String):JSONObject{
        return JSONObject()
    }

    //TODO エラー時の処理を記述するオーバライドメソッド
    fun errorProcessing(str:String){
        return
    }
    */
}
