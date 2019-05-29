package jp.ac.asojuku.st.noffication_de_study

import android.content.Context

//TODO:ユーザ回答履歴DB:未完成(0%)
class UserAnswersOpenHelper {
    //コンストラクタ
    fun UserAnswersOpenHelper(context: Context) {

    }

    //全問題解答検索
    fun find_all_user_answers() :ArrayList<ArrayList<String>>?{

        var rt = ArrayList<ArrayList<String>>()
        return rt
    }

    //問題解答検索
    fun find_user_answers(question_id:Int):ArrayList<ArrayList<String>>?{

        var rt = ArrayList<ArrayList<String>>()
        return rt
    }

    //レコード追加(引数にcolumnデータ
    fun add_record(){

    }

}