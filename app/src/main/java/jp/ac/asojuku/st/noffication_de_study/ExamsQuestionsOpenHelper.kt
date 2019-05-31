package jp.ac.asojuku.st.noffication_de_study

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class ExamsQuestionsOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "exams_questions", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "exams_questions";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "exams_id integer not null, " +
                    "exams_number varchar, " +
                    "question_id integer, " +
                    "question_number integer, " +
                    "PRIMARY KEY (exams_id,exams_number,question_id,question_number)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_all_questions(exams_id:Int,exams_number:String) :ArrayList<ArrayList<Int>>? {
        val thisDB = ExamsQuestionsOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where exams_id = " + exams_id + " and exams_number = " + exams_number
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()

            var array = ArrayList<ArrayList<Int>>()
            var bufferList:ArrayList<Int>

            bufferList = ArrayList()
            bufferList.add(cursor.getInt(0))
            array.add(bufferList)

            for(i in 0 until  cursor.count){
                bufferList = ArrayList()
                bufferList.add(cursor.getInt(2))
                bufferList.add(cursor.getInt(3))
                array.add(bufferList)
                cursor.moveToNext()
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:String,b_num:Int,c_num:Int,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("exams_id", q_id)
        values.put("exams_number", a_num)
        values.put("question_id", b_num)
        values.put("question_number", c_num)


        db.insertOrThrow(tableName, null, values)
    }
}



