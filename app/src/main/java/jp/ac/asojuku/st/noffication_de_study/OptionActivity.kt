package jp.ac.asojuku.st.noffication_de_study

import android.content.Intent
import android.content.SharedPreferences
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_option.*
import android.R.id.edit
import android.app.TimePickerDialog
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager
import android.preference.PreferenceDataStore
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.View.OnClickListener;
import android.widget.*
import android.support.v4.app.DialogFragment;


//TODO オプション画面:未完成(0%)
class OptionActivity : AppCompatActivity(),TimePickerFragment.OnTimeSelectedListener,TimePickerFragment2.OnTimeSelectedListener {
    private val RC_SIGN_IN = 7
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    override fun onSelected(hourOfDay: Int, minute: Int) {
        OA_Noffication_Time_Between1.text = "%1&02d:%2&02d".format(hourOfDay,minute) //timepickerによるtextviewの変更(未確認)
        OA_Noffication_Time_Between2.text = "%1&02d:%2&02d".format(hourOfDay,minute) //同上
    }


    val checkNDS = OA_NDS_Mode_BTN.isChecked()
    val checkSDS = OA_SDS_Mode_BTN.isChecked()
    val Spinner = OA_Noffication_Interval.getSelectedItemPosition()
    val option : SharedPreferences.Editor = getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE).edit()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        OA_NDS_Mode_BTN.setOnClickListener { regRule() }
        OA_SDS_Mode_BTN.setOnClickListener { regRule() }
        OA_Noffication_Time_Between1.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager,"StartTime_dialog")
            regRule()
            }
        OA_Noffication_Time_Between2.setOnClickListener {
            val dialog2 = TimePickerFragment2()
            dialog2.show(supportFragmentManager,"EndTime_dialog")
            regRule()
            }
        OA_Noffication_Interval.setOnClickListener { regRule() }

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
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

    //ルール登録
    fun regRule() {
        if(checkNDS){ //通知de勉強ボタンのチェック状態保存
            option.putBoolean("NDS_check",true).apply()
        }else{
            option.putBoolean("NDS_check",false).apply()
        }
        if(checkSDS){ //画面点灯de勉強ボタンのチェック状態保存
            option.putBoolean("SDS_check",true).apply()
        }else{
            option.putBoolean("SDS_check",false).apply()
        }
        option.putString("NDS_Start", OA_Noffication_Time_Between1.text.toString()).apply()
        option.putString("NDS_End",OA_Noffication_Time_Between2.text.toString()).apply()
        option.putInt("NDS_Interval",Spinner).apply() //通知間隔設定の保存
    }
}
