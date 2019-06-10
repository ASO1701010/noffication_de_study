package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class CorrectAnswerOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "correct_answer", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "correct_answer";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null, " +
                    "correct_answer_number integer, " +
                    "PRIMARY KEY (question_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_correct_answer(question_id:Int) :Int? {
        val thisDB = CorrectAnswerOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var result:Int = cursor.getInt(1)
            cursor.close()

            return result
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:Int) {
        val thisDB = CorrectAnswerOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("correct_answer_number", a_num)


        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"question_id = " + q_id,null)
        }
    }
}



