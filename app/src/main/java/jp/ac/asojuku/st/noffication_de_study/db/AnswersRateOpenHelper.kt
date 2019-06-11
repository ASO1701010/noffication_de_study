package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AnswersRateOpenHelper (var db:SQLiteDatabase) {

    val tableName:String = "answers_rate";

    fun find_rate(question_id:Int) :Double? {

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
        }catch (e:IndexOutOfBoundsException){
            return null
        }
    }
    fun add_record(q_id:Int , a_num:Double) {

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("answer_rate", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"question_id = " + q_id,null)
        }
    }
}



