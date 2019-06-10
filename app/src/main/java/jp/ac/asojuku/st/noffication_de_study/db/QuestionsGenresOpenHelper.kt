package jp.ac.asojuku.st.noffication_de_study.db


import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class QuestionsGenresOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "questions_genres", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "questions_genres";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null, " +
                    "genre_id integer, " +
                    "PRIMARY KEY (question_id,genre_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_question_genres(question_id:Int) :ArrayList<Int>? {
        val thisDB = QuestionsGenresOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var array = ArrayList<Int>()
            array.add(cursor.getInt(0))
            for(i in 0 until  cursor.count){
                array.add(cursor.getInt(1))
                cursor.moveToNext()
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun find_genre_questions(genre_id:Int):ArrayList<Int>? {
        val questions_genresDB = QuestionsGenresOpenHelper(mContext)
        val db = questions_genresDB.readableDatabase

        val query = "SELECT * FROM " + tableName + " where genre_id = " + genre_id
        val cursor = db.rawQuery(query, null)

        try{
            cursor.moveToFirst()
            var array = ArrayList<Int>()
            array.add(cursor.getInt(1))
            for(i in 0 until  cursor.count){
                array.add(cursor.getInt(0))
                cursor.moveToNext()
            }
            cursor.close()
            return array
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun add_record(q_id:Int , a_num:Int) {
        val questions_genresDB = QuestionsGenresOpenHelper(mContext)
        val db = questions_genresDB.readableDatabase

        val values = ContentValues()
        values.put("question_id", q_id)
        values.put("genre_id", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"question_id = " + q_id +" and genre_id = "+ a_num,null)
        }
    }
}



