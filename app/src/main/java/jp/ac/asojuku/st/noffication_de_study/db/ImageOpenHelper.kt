package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

class ImageOpenHelper(var db: SQLiteDatabase) {
    val tableName: String = "image";

    fun find_image(question_id: Int): ArrayList<String>? {

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        return try {
            cursor.moveToFirst()
            var array = ArrayList<String>()
            array.add(cursor.getString(0).toString())
            for (i in 0 until cursor.count) {
                array.add(cursor.getString(1).toString())
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
        values.put("question_id", q_id)
        values.put("file_name", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        } catch (e: SQLiteConstraintException) {
            db.update(tableName, values, "question_id = " + q_id, null)
        }

    }
}



