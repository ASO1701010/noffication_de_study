package jp.ac.asojuku.st.noffication_de_study

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LocalNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val answer = intent.getBooleanExtra("answer", false)
        val test = if (answer) {
            "正解です"
        } else {
            "外れです"
        }
        Toast.makeText(context, test, Toast.LENGTH_LONG).show()
    }

}