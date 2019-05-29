package jp.ac.asojuku.st.noffication_de_study

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import org.json.JSONObject


class AnswersOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "answers", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "answers";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null, " +
                    "answer_number integer, "+
                    "PRIMARY KEY (question_id,answer_number)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    //問題idを受け取って、対応する答えの選択肢を返す
    fun find_answers(question_id:Int) :ArrayList<Int>? {
        val thisDB = AnswersOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        try {
            cursor.moveToFirst()
            var array = ArrayList<Int>()
            array.add(cursor.getInt(0))
            for (i in 0 until cursor.count) {
                array.add(cursor.getInt(1))
                cursor.moveToNext();
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun add_record(q_id:Int , a_num:Int,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("answer_number", a_num)


        db.insertOrThrow(tableName, null, values)
    }
}



