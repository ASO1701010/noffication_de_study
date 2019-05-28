package jp.ac.asojuku.st.noffication_de_study

//画面遷移系のクラス
interface ScreenTransition {

    //TODO 画面遷移系：未完成（100%）
    //タイトル画面>出題オプション
    fun title_questionOption(user_id: Int) {

    }

    //出題解答>解答解説
    fun questionOption_question(question_id: Int, user_id: Int) {

    }

    //出題解答>解答解説
    fun question_answer(question_id: Int, answer_number: Int, user_id: Int) {

    }

    //出題解答>出題解答 （解答のスキップ）
    fun question_question(question_id: Int, answer_number: Int, user_id: Int) {

    }

    //出題解答>タイトル
    fun question_title(user_id: Int) {

    }

    //解答解説>出題解答
    fun answer_question(question_id: Int, answer_number: Int, user_id: Int) {

    }

    //タイトル>統計情報
    fun title_statics(user_id: Int) {

    }

    //タイトル>オプション
    fun title_option(user_id: Int) {

    }


    //戻るボタンを押したときの遷移
    //タイトル<出題オプション
    fun title_questionOption_back(user_id: Int) {

    }

    //出題オプション<出題解答
    fun questionOption_question_back(user_id: Int) {

    }

    //出題解答<解答解説
    fun question_answer_back(question_id: Int, answer_number: Int, user_id: Int) {

    }

    //解答解説<出題解答
    fun answer_question_back(question_id: Int, answer_number: Int, user_id: Int) {

    }

    //タイトル<統計情報
    fun title_statics_back(user_id: Int) {

    }

    //タイトル<オプション
    fun title_option_back(user_id: Int) {

    }


}