package jp.ac.asojuku.st.noffication_de_study.db



import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class GenresOpenHelper (var db:SQLiteDatabase){

    val tableName:String = "genres";

    fun find_genre(genre_id:Int) :String? {

        val query = "SELECT * FROM " + tableName+ " where genre_id = " + genre_id
        val cursor = db.rawQuery(query, null)
        try{
            cursor.moveToFirst()

            var result:String = cursor.getString(1)

            cursor.close()
            return result
        }catch (e: CursorIndexOutOfBoundsException){
            cursor.close()
            return null
        }
    }
    fun add_record(q_num:Int, a_num:String) {

        val values = ContentValues()
        values.put("genre_id",q_num)
        values.put("genre_name", a_num)

        try {
            db.insertOrThrow(tableName, null, values)
        }catch (e: SQLiteConstraintException){
            db.update(tableName,values,"genre_id = "+q_num,null)
        }
    }
}



