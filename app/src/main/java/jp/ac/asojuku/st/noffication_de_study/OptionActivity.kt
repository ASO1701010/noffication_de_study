package jp.ac.asojuku.st.noffication_de_study

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_option.*
import org.json.JSONObject

class OptionActivity : AppCompatActivity() {
    // Google SignIn
    private val rcSignIn = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var spEditor: SharedPreferences.Editor
    private lateinit var spGetter: SharedPreferences

    private val noticeIntervalItems = arrayOf("5", "10", "15", "20", "25", "30")

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        //テストデータ
        showNotification(1,"１＋１＝？\nア:0 イ:1 ウ:2 エ:3")

        spEditor = getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()

        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, noticeIntervalItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        OA_Noffication_Interval.adapter = adapter

        // SharedPreferencesから設定情報を取得し画面に反映
        spGetter = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        OA_NDS_Mode_BTN.isChecked = spGetter.getBoolean("NDS_check", false)
        OA_SDS_Mode_BTN.isChecked = spGetter.getBoolean("SDS_check", false)
        OA_Noffication_Time_Between1.text = spGetter.getString("NDS_Start", "09:00")
        OA_Noffication_Time_Between2.text = spGetter.getString("NDS_End", "21:00")
        OA_Noffication_Interval.setSelection(noticeIntervalItems.indexOf(spGetter.getString("NDS_Interval", "5")))

        // Google SignIn
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onResume() {
        super.onResume()

        OA_Back_BTN.setOnClickListener {
            finish()
        }

        OA_NDS_Mode_BTN.setOnClickListener {
            spEditor.putBoolean("NDS_check", OA_NDS_Mode_BTN.isChecked).apply()
        }

        OA_SDS_Mode_BTN.setOnClickListener {
            spEditor.putBoolean("SDS_check", OA_SDS_Mode_BTN.isChecked).apply()
        }

        OA_Noffication_Time_Between1.setOnClickListener {
            val nowTime = spGetter.getString("NDS_Start", "09:00")
            val nowTimeList: List<String> =
                if (nowTime.isNullOrEmpty()) listOf("21", "00") else nowTime.split(Regex(":"))
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfDay ->
                    val time = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                    OA_Noffication_Time_Between1.text = time
                    spEditor.putString("NDS_Start", time).apply()
                }, nowTimeList[0].toInt(), nowTimeList[1].toInt(), true
            ).show()
        }

        OA_Noffication_Time_Between2.setOnClickListener {
            val nowTime = spGetter.getString("NDS_End", "21:00")
            val nowTimeList: List<String> =
                if (nowTime.isNullOrEmpty()) listOf("21", "00") else nowTime.split(Regex(":"))
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfDay ->
                    val time = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                    OA_Noffication_Time_Between2.text = time
                    spEditor.putString("NDS_End", time).apply()
                }, nowTimeList[0].toInt(), nowTimeList[1].toInt(), true
            ).show()
        }

        OA_Noffication_Interval.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinner = parent as Spinner
                val select = spinner.selectedItem.toString()
                spEditor.putString("NDS_Interval", select).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Google SignIn
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, rcSignIn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == rcSignIn) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Googleアカウントのログインに失敗しました", Toast.LENGTH_LONG).show()
            }
        }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "認証に失敗しました", Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "[${user.displayName}]さんでログインしています", Toast.LENGTH_LONG).show()
            ApiPostTask {
                if (it.isNullOrEmpty()) {
                    Toast.makeText(this, "APIとの通信に失敗しました", Toast.LENGTH_LONG).show()
                } else {
                    val jsonObject = JSONObject(it)
                    val status = jsonObject.getString("status")
                    if (status == "S00") {
                        val userId = jsonObject.getJSONObject("data").getString("user_id")
                        val e: SharedPreferences.Editor = getSharedPreferences("user_data", MODE_PRIVATE).edit()
                        e.putString("user_id", userId).apply()
                    } else {
                        Toast.makeText(this, "ユーザー登録に失敗しました", Toast.LENGTH_LONG).show()
                    }
                }
            }.execute("add-user.php", hashMapOf("token" to user.uid).toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(question_id: Int, question_text: String) {
        val mChannel = NotificationChannel("0","問題通知", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        val contentView = RemoteViews(getPackageName(), R.layout.notifi_layout)

        val intent1 = Intent(this, StaticsActivity::class.java)
        val intent2 = Intent(this, StaticsActivity::class.java)
        val intent3 = Intent(this, StaticsActivity::class.java)
        val intent4 = Intent(this, StaticsActivity::class.java)

        // 通知ボタンタップ時に渡す値
        intent1.putExtra("question_id",question_id)
        intent1.putExtra("answer_number",1)
        intent2.putExtra("question_id",question_id)
        intent2.putExtra("answer_number",2)
        intent3.putExtra("question_id",question_id)
        intent3.putExtra("answer_number",3)
        intent4.putExtra("question_id",question_id)
        intent4.putExtra("answer_number",4)
        val pi1 = PendingIntent.getActivity(this, 1983418741, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton, pi1)
        val pi2 = PendingIntent.getActivity(this, 1983418742, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton2, pi2)
        val pi3 = PendingIntent.getActivity(this, 1983418743, intent3, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton3, pi3)
        val pi4 = PendingIntent.getActivity(this, 1983418744, intent4, PendingIntent.FLAG_UPDATE_CURRENT)
        contentView.setOnClickPendingIntent(R.id.notifyButton4, pi4)

        contentView.setTextViewText(R.id.notifiText,question_text)
        // Notify
        var notification = NotificationCompat.Builder(this,"0")
            .setSmallIcon(R.drawable.abc_ic_star_half_black_16dp)
            .setContent(contentView)
            .build()

        notificationManager.notify(0, notification);
    }
}