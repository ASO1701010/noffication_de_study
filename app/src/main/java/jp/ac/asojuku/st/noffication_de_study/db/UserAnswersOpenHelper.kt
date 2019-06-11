package jp.ac.asojuku.st.noffication_de_study.db

import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class UserAnswersOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "user_answers", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "user_answers";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "user_answer_id integer not null primary key, " +
                    "question_id integer, " +
                    "answer_choice integer, " +
                    "answer_time date " +
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_all_user_answers() :ArrayList<ArrayList<String>>? {
        val thisDB = UserAnswersOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()

            var array = ArrayList<ArrayList<String>>()
            var bufferlist:ArrayList<String>
            for (i in 0 until cursor.count) {
                bufferlist = ArrayList()
                bufferlist.add(cursor.getString(0).toString())
                bufferlist.add(cursor.getString(1).toString())
                bufferlist.add(cursor.getString(2).toString())
                bufferlist.add(cursor.getString(3).toString())
                array.add(bufferlist)
                cursor.moveToNext();
            }
            cursor.close()
            if(array.size==0){
                return  null
            }
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }

    fun find_user_answers(question_id:Int) :ArrayList<ArrayList<String>>? {
        val thisDB = UserAnswersOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()

            var array = ArrayList<ArrayList<String>>()
            var bufferlist:ArrayList<String>
            for (i in 0 until cursor.count) {
                bufferlist = ArrayList()
                bufferlist.add(cursor.getString(0).toString())
                bufferlist.add(cursor.getString(1).toString())
                bufferlist.add(cursor.getString(2).toString())
                bufferlist.add(cursor.getString(3).toString())
                array.add(bufferlist)
                cursor.moveToNext();
            }
            cursor.close()
            if(array.size==0){
                return  null
            }
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun add_record(a:Int , b:Int, c:Int, d:Int, db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("user_answer_id", a)
        values.put("question_id",b )
        values.put("answer_choice",c )
        values.put("answer_time",d )

        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"user_answer_id = " + a,null)
        }
    }
}



