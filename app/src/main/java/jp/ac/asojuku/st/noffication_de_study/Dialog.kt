package jp.ac.asojuku.st.noffication_de_study

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    interface OnTimeSelectedListener{
        fun onSelected(hourOfDay: Int,minute: Int)
    }
    private lateinit var listener:OnTimeSelectedListener
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is TimePickerFragment.OnTimeSelectedListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(context,this,hour,minute,true)
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener.onSelected(hourOfDay, minute)
    }
}
class TimePickerFragment2 : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    interface OnTimeSelectedListener{
        fun onSelected(hourOfDay: Int,minute: Int)
    }
    private lateinit var listener:OnTimeSelectedListener
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is TimePickerFragment2.OnTimeSelectedListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(context,this,hour,minute,true)
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener.onSelected(hourOfDay, minute)
    }
}