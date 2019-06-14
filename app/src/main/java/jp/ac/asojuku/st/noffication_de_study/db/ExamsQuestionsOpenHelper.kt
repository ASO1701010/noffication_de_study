package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

class ExamsQuestionsOpenHelper(var db: SQLiteDatabase) {
    val tableName: String = "exams_questions";

    fun find_all_questions(exams_id: Int, exams_number: String): ArrayList<ArrayList<Int>>? {
        val query =
            "SELECT * FROM " + tableName + " where exams_id = " + exams_id + " and exams_number = '" + exams_number + "'"
        val cursor = db.rawQuery(query, null)

        try {
            cursor.moveToFirst()

            var array = ArrayList<ArrayList<Int>>()
            var bufferList: ArrayList<Int>

            for (i in 0 until cursor.count) {
                bufferList = ArrayList()
                bufferList.add(cursor.getInt(2))
                bufferList.add(cursor.getInt(3))
                array.add(bufferList)
                cursor.moveToNext()
            }
            cursor.close()
            return array
        } catch (e: CursorIndexOutOfBoundsException) {
            return null
        }
    }

    fun add_record(q_id: Int, a_num: String, b_num: Int, c_num: Int) {
        val values = ContentValues()
        values.put("exams_id", q_id)
        values.put("exams_number", a_num)
        values.put("question_id", b_num)
        values.put("question_number", c_num)

        try {
            db.insertOrThrow(tableName, null, values)
        } catch (e: SQLiteConstraintException) {
            db.update(
                tableName,
                values,
                "exams_id = " + q_id + " and exams_number = '" + a_num + "' and question_id = " + b_num + " and question_number = " + c_num,
                null
            )
        }
    }
}



