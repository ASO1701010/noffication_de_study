package jp.ac.asojuku.st.noffication_de_study

import java.io.Serializable

class ExamData (id:Int, name:String, number:String):Serializable{

    public val exams_id = id
    public val exams_name = name
    public val exams_number = number

    public var question_list = ArrayList<Int>() //question_idが入るリスト
    public var answered_list = ArrayList<Int>() //question_listに対応するユーザの解答が入るリスト
    public var isCorrect_list = ArrayList<Boolean>() //question_listに対応するユーザの解答が正解だったかが入るリスト

    public var question_current = 0
    public var question_next = 0

    //list
    public fun set_list_data(list:ArrayList<Int>){
        this.question_list = list
        for(i in 1..question_list.size){
            list.add(999)
        }
    }

}