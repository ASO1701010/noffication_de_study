package jp.ac.asojuku.st.noffication_de_study

import android.os.AsyncTask
import android.util.Log
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class APConnectionManager {
    val server = "http://eve.main.jp/noffication-de-study/api/"

    var json = JSONObject()

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

    inner class ApiGetTask(private val api_name: String, private val param: HashMap<String, String>) :
        AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String? {
            val api = server + api_name
            val urlBuilder = HttpUrl.parse(api)!!.newBuilder()

            param.forEach { name, value -> urlBuilder.addQueryParameter(name, value) }

            val request = Request.Builder().url(urlBuilder.toString()).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()

            val data = response.body()!!.string()
            // val jsonObject = JSONObject(data)

            json = JSONObject(data)

            return data
        }
    }

    inner class ApiPostTask(private val api_name: String, private val param: HashMap<String, String>) :
        AsyncTask<String, String, String>() {
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