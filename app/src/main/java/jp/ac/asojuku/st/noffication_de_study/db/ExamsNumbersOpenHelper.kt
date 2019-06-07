package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ExamsNumbersOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "exams_numbers", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "exams_numbers";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "exams_id integer not null, " +
                    "exams_number varchar, " +
                    "PRIMARY KEY (exams_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_exams_numbers(exams_id:Int) :ArrayList<String>? {
        val thisDB = ExamsNumbersOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where exams_id = " + exams_id
        val cursor = db.rawQuery(query, null)

            try{
            cursor.moveToFirst()
            var array = ArrayList<String>()
            array.add(cursor.getString(0))
            for(i in 0 until  cursor.count){
                array.add(cursor.getString(1))
                cursor.moveToNext()
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:String) {
        val thisDB = ExamsNumbersOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val values = ContentValues()
        values.put("exams_id", q_id)
        values.put("exams_number", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"exams_id = " + q_id,null)
        }
    }
}



