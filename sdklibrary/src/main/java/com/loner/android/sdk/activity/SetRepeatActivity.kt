package com.loner.android.sdk.activity

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.RepeatTimerListener
import com.loner.android.sdk.activity.adapter.RepeatTimerAdapter
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_set_repeation.*
import java.util.*

class SetRepeatActivity : AppCompatActivity(), DatePicker.OnDateChangedListener, RepeatTimerListener {
    private lateinit var listView: ListView
    private var itemRepeatTime = intArrayOf(R.string.never, R.string.until_turned_off, R.string.once, R.string.twice, R.string.until_a_specific_item)
    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var now: Calendar
    private lateinit var pickerLinearLayout: LinearLayout
    private lateinit var repeatTimerAdapter: RepeatTimerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_set_repeation)
        setRepeatActivityInstance(this)
        init()
        btnBackButton.setOnClickListener {
            finish()
        }

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
           // if (0 > TimerValidation.getMinuteDifferenceTime(TimerValidation.getTimePickerTime(hourOfDay, minute, datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
                now = Calendar.getInstance()
                timePicker.currentHour = now.get(Calendar.HOUR_OF_DAY)
                timePicker.currentMinute = now.get(Calendar.MINUTE)
           // }
        }

    }

    private fun init() {
        timePicker = findViewById(R.id.timepicker)
        datePicker = findViewById(R.id.datePicker)
        pickerLinearLayout = findViewById(R.id.pickerLayout)
        removeYearDatePicker(datePicker)
        datePicker.minDate = Date().time - 10000
        listView = findViewById(R.id.repeatList)
        repeatTimerAdapter = RepeatTimerAdapter(applicationContext, itemRepeatTime, this)
        listView.setAdapter(repeatTimerAdapter)

    }

    private fun removeYearDatePicker(datePicker: DatePicker?) {
        val yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android")
        try {
            if (yearSpinnerId != 0) {
                val yearSpinner = datePicker?.findViewById<View>(yearSpinnerId)
                if (yearSpinner != null) {
                    yearSpinner.visibility = View.GONE
                }
            }

        } catch (e: SecurityException) {
            Log.d("ERROR", e.message)
        } catch (e: IllegalArgumentException) {
            Log.d("ERROR", e.message)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

    }



    companion object {
        lateinit var instance :  SetRepeatActivity
        fun setRepeatActivityInstance(instance: SetRepeatActivity){
            this.instance = instance
        }

        fun getRepeatActivityInstance(): SetRepeatActivity {
            return instance
        }
    }

    override fun setRepeat(repeatType: String) {
       // TimerConfiguration.getInstance().setListRepeatTimerTyepe(repeatType)
        numberPickerView(repeatType)
        repeatTimerAdapter.notifyDataSetChanged()
    }

    private fun numberPickerView(repeatType: String) {
        if (repeatType == getText(R.string.until_a_specific_item)) {
            pickerLinearLayout.visibility = View.VISIBLE
            desciptionUntilSpecific.visibility = View.VISIBLE
        } else {
            pickerLinearLayout.visibility = View.GONE
            desciptionUntilSpecific.visibility = View.GONE
        }
    }
}
