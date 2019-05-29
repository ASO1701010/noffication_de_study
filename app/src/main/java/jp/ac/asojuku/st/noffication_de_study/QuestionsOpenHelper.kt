package jp.ac.asojuku.st.noffication_de_study

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList


class QuestionsOpenHelper (var mContext: Context?) : SQLiteOpenHelper(mContext, "questions", null, 1) {
    // 第１引数 :
    // 第２引数 : データベースの名称
    // 第３引数 : null
    // 第４引数 : データベースのバージョン
    val tableName:String = "questions";

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + tableName + " ( " +
                    "question_id integer not null autoincrement, " +
                    "question varchar, "+
                    "is_have_image boolean, "+
                    "comment varchar, "+
                    "update_date date, "+
                    "PRIMARY KEY (question_id)"+
                    ");")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    //問題idを渡して、
    fun find_question(question_id:Int) :ArrayList<String>? {
        val thisDB = QuestionsOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM "+ tableName +" where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        var array = ArrayList<String>()

        array.add(cursor.getString(0).toString())
        array.add(cursor.getString(1).toString())
        array.add(cursor.getString(2).toString())

        cursor.close()
        return array
    }

    fun find_comment(question_id:Int) :ArrayList<String>? {
        val thisDB = QuestionsOpenHelper(mContext)
        val db = thisDB.readableDatabase

        val query = "SELECT * FROM "+ tableName +" where question_id = " + question_id
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        var array = ArrayList<String>()

        array.add(cursor.getString(0).toString())
        array.add(cursor.getString(3).toString())
        array.add(cursor.getString(4).toString())

        cursor.close()
        return array
    }
    fun add_record(a:Int , b:String, c:Boolean, d:String, e:Int, db:SQLiteDatabase) {

        val values = ContentValues()
        values.put("question_id", a)
        values.put("question",b )
        values.put("is_have_image",c )
        values.put("comment",d )
        values.put("update_date",e )

        db.insertOrThrow(tableName, null, values)
    }
}