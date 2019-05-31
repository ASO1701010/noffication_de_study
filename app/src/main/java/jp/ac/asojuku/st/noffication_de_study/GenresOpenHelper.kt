package jp.ac.asojuku.st.noffication_de_study



import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class GenresOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "genres", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "genres";
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "genre_id integer not null primary key autoincrement, " +
                    "genre_name varchar" +
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun find_genre(genre_id:Int) :String? {
        val thisDB = GenresOpenHelper(mContext)
        val db = thisDB.readableDatabase

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
    fun add_record( a_num:String,db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("genre_name", a_num)

        db.insertOrThrow(tableName, null, values)
    }
}



