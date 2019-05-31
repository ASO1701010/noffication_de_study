package jp.ac.asojuku.st.noffication_de_study

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AnswersRateOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "answers_rate", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "answers_rate";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null, " +
                    "answer_rate double, " +
                    "PRIMARY KEY (question_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_rate(question_id:Int) :Double? {
        val thisDB = AnswersRateOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)
        try{
            cursor.moveToFirst()
            var result:Double
            result = cursor.getDouble(1)
            cursor.close()
            return result
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun find_all_rate() :ArrayList<ArrayList<Double>>? {
        val answerDB = AnswersRateOpenHelper(mContext)
        val db = answerDB.readableDatabase

        val query = "SELECT * FROM " + tableName
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var array = ArrayList<ArrayList<Double>>()
            var bufferList = ArrayList<Double>()

            for(i in 0 until  cursor.count){
                bufferList.add(cursor.getDouble(0))
                bufferList.add(cursor.getDouble(1))
                array.add(bufferList)
                bufferList.clear()
                cursor.moveToNext()
            }
            cursor.close()
            if(array.get(0).size==0){
                return null
            }
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:Double,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("answer_rate", a_num)


        db.insertOrThrow(tableName, null, values)
    }
}



