package jp.ac.asojuku.st.noffication_de_study

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import jp.ac.asojuku.st.noffication_de_study.db.AnswersRateOpenHelper
import kotlinx.android.synthetic.main.activity_statics.*
import org.json.JSONObject

//TODO 統計情報画面:未完成(20%)
class StaticsActivity : AppCompatActivity(), FragmentMyRecord.OnFragmentInteractionListener,
    FragmentQuestion.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    //定数はすべて仮の値
//    val user_id: Int = 12345678 //ユーザID
//    var my_record: String = "testRecord" //my記録
//    var correct_rate: String = "999%" //正答率
//    var text_print: String = "testTextPrint" //表示に利用する

    var jsondata = JSONObject() // get_statics() の戻り値を格納。 統計情報が入っております。

    private val tabTitle = arrayOf<CharSequence>("my記録", "問題ごと")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statics)

        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                return when (position) {
                    0 -> FragmentMyRecord()
                    1 -> {
                        val fragment = FragmentQuestion()
                        fragment.arguments = Bundle().apply {
                            val dbHelper = AnswersRateOpenHelper(SQLiteHelper(this@StaticsActivity).readableDatabase)
                            val data = dbHelper.find_all_rate()
                            putStringArrayList("DATA", data)
                        }
                        return fragment
                    }
                    else -> null
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitle[position]
            }

            override fun getCount(): Int {
                return tabTitle.size
            }
        }

        val viewPager = viewPager
        viewPager.offscreenPageLimit = tabTitle.size
        viewPager.adapter = adapter
        val tabLayout = tabLayout
        tabLayout.setupWithViewPager(viewPager)

        SA_Back_BTN.setOnClickListener {
            finish()
        }
    }

    //統計情報取得
    /*
    fun get_statics() {
        var result_flg = true
        var json = JSONObject()
        ApiGetTask {
            if (!it.isNullOrEmpty()) {
                Log.d("it", it)
                json = JSONObject(it)
            } else {
                Toast.makeText(this, "APIの通信に失敗しました(´･ω･`)", Toast.LENGTH_SHORT).show()
                result_flg = false
            }
        }.execute("get-statistics-info.php")

        if (result_flg) {
            jsondata = json
        }
    }
    */
}
