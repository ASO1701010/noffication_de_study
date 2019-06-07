package jp.ac.asojuku.st.noffication_de_study

import android.os.AsyncTask
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiPostTask(callback: (String?) -> Unit) : AsyncTask<String, Unit, String>() {
    var callback = callback
    val server = "http://eve.main.jp/noffication-de-study/api/"

    override fun doInBackground(vararg params: String): String {
        val api = server + params[0]

        val request: Request

        if (params.size == 2) {
            var paramString: String = params[1]
            paramString = paramString.substring(1, paramString.length - 1)
            val keyValuePairs = paramString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val param: HashMap<String, String> = HashMap()
            for (pair in keyValuePairs) {
                val entry = pair.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                param.put(entry[0].trim { it <= ' ' }, entry[1].trim { it <= ' ' })
            }

            val formBuilder = FormBody.Builder()
            param.forEach { name, value -> formBuilder.add(name, value) }
            val body:FormBody = formBuilder.build()
            request = Request.Builder().url(api).post(body).build()
        } else {
            request = Request.Builder().url(api).build()
        }

        val okHttpClient = OkHttpClient.Builder().build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()

        val data = response.body()!!.string()

        return data
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        callback(result)
    }
}
