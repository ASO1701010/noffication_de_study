package jp.ac.asojuku.st.noffication_de_study

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import jp.ac.asojuku.st.noffication_de_study.db.QuestionsOpenHelper
import kotlinx.android.synthetic.main.fragment_fragment_question.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentQuestion : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()

        val data = arguments?.getStringArrayList("DATA")
        if (data != null) {
            // Stringを配列に変換する
            var i = 0
            val array = ArrayList<ArrayList<String>>()
            var bufferList: ArrayList<String>
            while (i < data.size) {
                bufferList = ArrayList()
                val l1 = data[i]
                i++
                val l2 = data[i]
                i++
                bufferList.add(l1)
                bufferList.add(l2)
                array.add(bufferList)
            }
            // ListViewに表示する
            val listView = listView as ListView
            val list = ArrayList<QuestionStatisticsItem>()
            val qsAdapter = activity?.let { QuestionStatisticsAdapter(it) }
            val questionHelper = activity?.let { SQLiteHelper(it).readableDatabase }?.let { QuestionsOpenHelper(it) }

            for (data in array) {
                val item = QuestionStatisticsItem()
                item.setId(data[0].toLong())
                item.setTitle(questionHelper!!.find_question(data[0].toInt())!![1])
                item.setRate((data[1].toDouble() * 100).toString() + " %")
                list.add(item)
            }

            qsAdapter!!.setFoodList(list)
            qsAdapter.notifyDataSetChanged()

            listView.adapter = qsAdapter

            listView.setOnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position) as QuestionStatisticsItem
                val questionId = item.getId()
                Toast.makeText(activity, questionId.toString(), Toast.LENGTH_LONG).show()
                /* クリックすると問題画面へ遷移する
                val intent = Intent(activity, OptionActivity::class.java)
                startActivity(intent)
                */
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_question, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentQuestion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
