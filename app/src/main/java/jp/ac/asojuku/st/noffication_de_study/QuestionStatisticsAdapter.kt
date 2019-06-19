package jp.ac.asojuku.st.noffication_de_study

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class QuestionStatisticsAdapter(internal var context: Context) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var statisticsItem: ArrayList<QuestionStatisticsItem>

    init {
        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setFoodList(statisticsItem: ArrayList<QuestionStatisticsItem>) {
        this.statisticsItem = statisticsItem
    }

    override fun getCount(): Int {
        return try {
            statisticsItem.size
        } catch (e: Exception) {
            0
        }
        // return statisticsItem.size
    }

    override fun getItem(position: Int): Any {
        return statisticsItem[position]
    }

    override fun getItemId(position: Int): Long {
        return statisticsItem[position].getId()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        convertView = layoutInflater!!.inflate(R.layout.statistics_question_list_item, parent, false)

        (convertView.findViewById<View>(R.id.title) as TextView).text = statisticsItem[position].getTitle()
        (convertView.findViewById<View>(R.id.rate) as TextView).text = statisticsItem[position].getRate()

        return convertView
    }
}