package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class CorrectAnswerOpenHelper (var db:SQLiteDatabase) {

    val tableName:String = "correct_answer";

    fun find_correct_answer(question_id:Int) :Int? {

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



