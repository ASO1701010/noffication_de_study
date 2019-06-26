package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

class CorrectAnswerOpenHelper(var db: SQLiteDatabase) {

    val tableName: String = "correct_answer";

    fun find_correct_answer(question_id: Int): Int? {
        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        return try {
            cursor.moveToFirst()
            var result = 0
            for (i in 0 until cursor.count) {
                result = cursor.getInt(1)
            }
            cursor.close()

            result
        } catch (e: CursorIndexOutOfBoundsException) {
            cursor.close()
            null
        }
    }

    fun add_record(q_id: Int, a_num: Int) {
        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("correct_answer_number", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        } catch (e: SQLiteConstraintException) {
            db.update(tableName, values, "question_id = " + q_id, null)
        }
    }
}



