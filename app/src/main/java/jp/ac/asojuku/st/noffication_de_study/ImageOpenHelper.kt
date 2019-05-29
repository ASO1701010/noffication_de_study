package jp.ac.asojuku.st.noffication_de_study

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import org.json.JSONObject


class ImageOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "image", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "image";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null, " +
                    "file_name varchar, " +
                    "PRIMARY KEY (question_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_image(question_id:Int) :ArrayList<String>? {
        val thisDB = ImageOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var array = ArrayList<String>()
            array.add(cursor.getString(0).toString())
            for(i in 0 until  cursor.count){
                array.add(cursor.getString(1).toString())
                cursor.moveToNext()
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun add_record(q_id:Int , a_num:String,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("file_name", a_num)

        db.insertOrThrow(tableName, null, values)
    }
}



