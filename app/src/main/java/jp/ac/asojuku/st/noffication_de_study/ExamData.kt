package jp.ac.asojuku.st.noffication_de_study

class ExamData (id:Int, name:String, number:String){

    public val exams_id = id
    public val exams_name = name
    public val exams_number = number

    public var question_list = ArrayList<Int>()
    public var answered_list = ArrayList<Int>()

    public var question_current = 0
    public var question_next = 0

    public fun set_list_data(list:ArrayList<Int>){
        this.question_list = list
        for(i in 1..question_list.size){
            list.add(999)
        }
    }

}