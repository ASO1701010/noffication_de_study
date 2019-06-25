package jp.ac.asojuku.st.noffication_de_study

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import jp.ac.asojuku.st.noffication_de_study.db.AnswersOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import jp.ac.asojuku.st.noffication_de_study.db.UserAnswersOpenHelper
import kotlinx.android.synthetic.main.activity_question.*
import java.lang.Exception
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class QuestionActivity : AppCompatActivity() {

    lateinit var examData: ExamData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        examData = intent.getSerializableExtra("exam_data") as ExamData
        /////////////////デバッグ用///////////////////////////
//        examData.question_list[0]
        /////////////////デバッグ用///////////////////////////

        choiceNextQuestion() //次の問題を読み込んでおく

        if (examData.question_current == 9999) { //表示する問題がないとき終了する
            printResult()
        } else {
            printQuestion() //問題文の表示

            //ボタンの設定
            AA_Answer_0.setOnClickListener { choiceAnswer(0) }
            AA_Answer_1.setOnClickListener { choiceAnswer(1) }
            AA_Answer_2.setOnClickListener { choiceAnswer(2) }
            AA_Answer_3.setOnClickListener { choiceAnswer(3) }
            AA_End_BTN.setOnClickListener {
                if (examData.isCorrect_list.size == 0) {
                    finish()
                } else {
                    printResult()
                }
            }
            AA_Next_BTN.setOnClickListener { skipQuestion() }
        }

    }

//    //コンストラクタ
//    fun atFirst() {
//        //集計か出題かを判定？
//    }

    //次の問題設定
    fun choiceNextQuestion() {
        if (examData.question_current == 0) {
            examData.question_current = examData.question_list[0]
            try {
                examData.question_next = examData.question_list[1]
            } catch (e: java.lang.IndexOutOfBoundsException) {
                examData.question_next = 9999
            }
        } else {
            examData.question_current = examData.question_next

            try {
                //次の問題IDを取得
                examData.question_next = examData.question_list[examData.isCorrect_list.size + 1]

            } catch (e: IndexOutOfBoundsException) {
                //次の問題が存在しない場合は次に9999を設定する
                examData.question_next = 9999
            }
        }


    }

    //問題表示
    fun printQuestion() {
        val questions = SQLiteHelper(this)
        val db = questions.readableDatabase
        val QOH = QuestionsOpenHelper(db)
        val question_arr: ArrayList<String>? = QOH.find_question(examData.question_current)
//        var question_arr: ArrayList<String>? = QOH.find_question(0)
        val question_str: String
        if (question_arr == null) {
            question_str = "問題文がありません"
        } else {
            question_str = question_arr[1]
        }

        textView4.setText(question_str)

    }

    //解答選択
    fun choiceAnswer(choice_number: Int) {
        //登録処理
        regAnswer(choice_number)
        //画面遷移
//        val intent = Intent(this, AnswerActivity::class.java)
        //Toastを表示する処理
        if (examData.isCorrect_list.get(examData.isCorrect_list.size - 1)) {//正解のとき
            Toast.makeText(this, "正解です！", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "不正解です！", Toast.LENGTH_SHORT).show()
        }

        startActivity<AnswerActivity>("exam_data" to examData)


    }

    //スキップ
    fun skipQuestion() {
        //DBにスキップしたとして999を登録して次の問題画面に画面遷移
        regAnswer(9999)
//        val intent = Intent(this, QuestionActivity::class.java)
        startActivity<QuestionActivity>("exam_data" to examData)
        finish()
    }

    //解答登録
    fun regAnswer(choice_number: Int) {
        //自分の解答を登録
        examData.answered_list.add(choice_number)
        //正解をDBから取得
        val answers = SQLiteHelper(this)
        val db = answers.readableDatabase
        val AOH = AnswersOpenHelper(db)
        val answer = AOH.find_answers(examData.question_current)?.get(1) //正しい正解
        var isCorrected = false //正解だった場合にtrueにする
        if (choice_number == answer) {
            isCorrected = true
        }
        examData.isCorrect_list.add(isCorrected)//解いた問題が正解だったかどうかがBoolean型で入る


    }

//    なんか使わないっぽい？（クラス一覧参照）
//    //結果集計
//    fun collectResult() {
//
//    }


    //結果表示
    fun printResult() {
        //解いた問題数を取得
        val BR: String? = System.getProperty("line.separator") //改行用コードを取得
        val answerCount = examData.isCorrect_list.size.toDouble()
        val skipCount = examData.answered_list.count { it == 9999 }//うまく動作しない可能性
        var skipMsg: String = ""
        if (skipCount > 0) {
            skipMsg = BR + "スキップ数:" + skipCount
        }

        //正解数を取得
        val correctedCount = examData.isCorrect_list.filter { it == true }.count().toDouble()
        //正答率を計算
        val answerRate = correctedCount / answerCount * 100.00
        val answerRateStr: String = String.format("%4.1f", answerRate)
        //ポップアップ用のビルダー
        val builder = AlertDialog.Builder(this)
        if (answerRate < 100) { //ミスがある場合、間違った問題を解くボタンを表示させる
            builder.setMessage("正答率:" + answerRateStr + "%" + skipMsg)
                .setCancelable(false)//範囲外タップによるキャンセルを不可にする
                .setNeutralButton("間違った問題を解く") { dialog, which ->
                    //間違った問題のリストを用意して問題解答画面に遷移する処理
//                    var tempQuestionList = this.examData.question_list
                    val tempQuestionList: ArrayList<Int> = ArrayList()
                    for (question_id in examData.question_list) {
                        tempQuestionList.add(question_id)
                    }
                    examData.question_list.clear() //問題リストの初期化
                    examData.answered_list.clear() //解答リストの初期化
                    examData.question_current = 0
                    examData.question_next = 0
                    for (i in 0..examData.isCorrect_list.size - 1) {
                        val isCollected = examData.isCorrect_list.get(i)
                        if (!isCollected) {
                            examData.question_list.add(tempQuestionList[i]) //間違ったquestion_idを詰め込んでいく
                        }
                    }
                    examData.isCorrect_list.clear()
//                    val intent = Intent(this, AnswerActivity::class.java)
////                    startActivity(intent)
                    startActivity<QuestionActivity>("exam_data" to this.examData)
                    finish()

                }
                .setPositiveButton("終了") { dialog, which ->
                    //タイトル画面に戻る処理
                    examData.question_list.clear() //問題リストの初期化
                    examData.answered_list.clear() //解答リストの初期化
                    val intent = Intent(this, TitleActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .show()
        } else {
            builder.setMessage("正答率:100%!!!")
                .setCancelable(false)//範囲外タップによるキャンセルを不可にする
                .setNeutralButton("おめでとう！！") { dialog, which ->
                    //タイトル画面に戻る処理
                    examData.question_list.clear() //問題リストの初期化
                    examData.answered_list.clear() //解答リストの初期化
                    val intent = Intent(this, TitleActivity::class.java)
                    startActivity(intent)
                    finish()
                }.show()
        }
    }

    fun regResult() {
        val date: Date = Date()
        val test1: String = DateFormat.format("yyyy-MM-dd", date).toString()
//        Log.d("tetes",hashMapOf(
//            "question_id" to exam_data.question_current,
//            "answer_choice" to exam_data.answered_list.get(exam_data.question_current),
//            "answer_time" to test1).toString())
        ApiPostTask {
            Log.d("tetes", it)
            if (JSONObject(it).getString("status") != "E00") {
                Toast.makeText(this, "APIの通信に成功しました(｀・ω・´)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "APIの通信に失敗しました(´･ω･`)", Toast.LENGTH_SHORT).show()
            }
        }.execute(
            "add-answer.php", hashMapOf(
                "question_id" to examData.question_current,
                "answer_choice" to examData.answered_list.get(examData.question_current),
                "answer_time" to test1
            ).toString()
        )

    }


}