package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

class ExamsNumbersOpenHelper(var db: SQLiteDatabase) {
    val tableName: String = "exams_numbers";

    fun find_exams_numbers(exams_id: Int): ArrayList<String>? {

        val query = "SELECT * FROM $tableName where exam_id = $exams_id"
        val cursor = db.rawQuery(query, null)

        val array = ArrayList<String>()
        return try {
            cursor.moveToFirst()
            array.add(cursor.getString(0))
            for (i in 0 until cursor.count) {
                array.add(cursor.getString(1))
                cursor.moveToNext()
            }
            cursor.close()
            array
        } catch (e: CursorIndexOutOfBoundsException) {
            cursor.close()
            null
        }
    }

    fun add_record(q_id: Int, a_num: String) {
        val values = ContentValues()
        values.put("exam_id", q_id)
        values.put("exams_number", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        } catch (e: SQLiteConstraintException) {
            db.update(tableName, values, "exam_id = $q_id and exams_number = '$a_num'", null)
        }
    }
}



