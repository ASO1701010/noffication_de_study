package jp.ac.asojuku.st.noffication_de_study

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

class LocalNotificationForegroundService : Service() {
    lateinit var mReceiver: BroadcastReceiver

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val spGetter = getSharedPreferences("user_data", MODE_PRIVATE)
        val receiver = object : BroadcastReceiver() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == Intent.ACTION_SCREEN_ON) {
                    // 四択問題・二択問題どちらを出題するか設定から読み込み出題
                    val service = LocalNotificationScheduleService()
                    when (spGetter.getString("way_radio_select", "four")) {
                        "four" -> {
                            service.fourQuestion(context)
                        }
                        "two" -> {
                            service.twoQuestion(context)
                        }
                    }
                }
            }
        }

        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_ON))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("channel_screen_question", "サービス", NotificationManager.IMPORTANCE_NONE)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(notificationChannel)
        }
        */

        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 777, Intent(applicationContext, TitleActivity::class.java), 0)

        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setChannelId("channel_screen_question")
            .setContentTitle("通知de勉強")
            .setContentText("サービス作動中")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()
        notification.flags = Notification.FLAG_NO_CLEAR
        notificationManager.notify(99999, notification)

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(mReceiver)
    }

}
