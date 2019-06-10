package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ExamsOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "exams", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "exams";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "exams_id integer not null, " +
                    "exams_name varchar, " +
                    "PRIMARY KEY (exams_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_exam_name(exams_id:Int) :String? {
        val thisDB = ExamsOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where exams_id = " + exams_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var result:String = cursor.getString(1)

            cursor.close()
            return result
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun find_exam_id(exams_id:Int) :String? {
        val thisDB = ExamsOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where exams_id = " + exams_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var result:String = cursor.getString(1)

            cursor.close()
            return result
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:String,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("exams_id", q_id)
        values.put("exams_name", a_num)


        db.insertOrThrow(tableName, null, values)
    }
}



