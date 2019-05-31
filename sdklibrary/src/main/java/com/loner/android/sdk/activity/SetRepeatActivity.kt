package com.loner.android.sdk.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.RepeatTimerListener
import com.loner.android.sdk.activity.adapter.RepeatTimerAdapter
import com.loner.android.sdk.dailogs.LonerDailog
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.utils.TimerValidation
import kotlinx.android.synthetic.main.activity_set_repeation.*
import java.util.*

class SetRepeatActivity : Activity(), DatePicker.OnDateChangedListener, RepeatTimerListener {
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
        setContentView(R.layout.activity_set_repeation)
        setRepeatActivityInstance(this)
        init()
        btnBackButton.setOnClickListener {
            backButtonPress()
        }
        if (TimerConfiguration.getInstance().getListRepeatTimerType(this) == getText(R.string.until_a_specific_item)) {
            timePicker.currentHour = TimerConfiguration.getInstance().getListHourTimerPicker(this)
            timePicker.currentMinute = TimerConfiguration.getInstance().getListMinuteTimerPicker(this)
            val day = TimerConfiguration.getInstance().getListDayofDatePicker(this)
            val month = TimerConfiguration.getInstance().getListMonthofDatePicker(this)
            val year = TimerConfiguration.getInstance().getListYearofDatePicker(this)
            datePicker.init(year, month, day, this)
        } else {
            now = Calendar.getInstance(Locale.ENGLISH)
            timePicker.currentHour = now.get(Calendar.HOUR_OF_DAY)
            timePicker.currentMinute = now.get(Calendar.MINUTE)
            datePicker.init(TimerValidation.year, TimerValidation.month, TimerValidation.dayOfMonth, this)

        }

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            if (0 > TimerValidation.getMinuteDifferenceTime(TimerValidation.getTimePickerTime(hourOfDay, minute, datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
                now = Calendar.getInstance()
                timePicker.currentHour = now.get(Calendar.HOUR_OF_DAY)
                timePicker.currentMinute = now.get(Calendar.MINUTE)
            }
        }

    }

    private fun backButtonPress() {
        val backIntent = Intent()
        if (TimerConfiguration.getInstance().getListRepeatTimerType(this).equals(getText(R.string.until_a_specific_item))) {
            if (TimerValidation.IsCurrentTimeSame(TimerValidation.getTimePickerTime(timePicker.currentHour, timePicker.currentMinute, datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
                backIntent.putExtra("until_specfic_time", TimerValidation.getTimePickerTime(timePicker.currentHour, timePicker.currentMinute, datePicker.year, datePicker.month, datePicker.dayOfMonth))
                TimerConfiguration.getInstance().setListHourTimerPicker(this,timePicker.currentHour)
                TimerConfiguration.getInstance().setListMinuteTimerPicker(this,timePicker.currentMinute)
                TimerConfiguration.getInstance().setListDayofDatePicekr(this,datePicker.dayOfMonth)
                TimerConfiguration.getInstance().setListMonthofDatePicekr(this,datePicker.month)
                TimerConfiguration.getInstance().setListYearofDatePicekr(this,datePicker.year)
                backIntent.putExtra("timer_repeat_type", TimerConfiguration.getInstance().getListRepeatTimerType(this))
                setResult(105, backIntent)
                this@SetRepeatActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            } else { LonerDailog.getInstance().showAlertDialog(this@SetRepeatActivity, null, getText(R.string.check_in_time_current).toString(),getText(R.string.okay).toString())
            }

        } else {
            backIntent.putExtra("timer_repeat_type", TimerConfiguration.getInstance().getListRepeatTimerType(this))
            setResult(105, backIntent)
            this@SetRepeatActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()

        }
    }

    private fun init() {
        timePicker = findViewById(R.id.timepicker)
        datePicker = findViewById(R.id.datePicker)
        pickerLinearLayout = findViewById(R.id.pickerLayout)
        removeYearDatePicker(datePicker)
        datePicker.minDate = Date().time - 10000
        listView = findViewById(R.id.repeatList)
        numberPickerView(TimerConfiguration.getInstance().getListRepeatTimerType(this))
        repeatTimerAdapter = RepeatTimerAdapter(applicationContext, itemRepeatTime, this)
        listView.adapter = repeatTimerAdapter

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
        if (0 > TimerValidation.getMinuteDifferenceTime(TimerValidation.getTimePickerTime(timePicker.currentHour, timePicker.currentMinute, datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
            now = Calendar.getInstance()
            timePicker.currentHour = now.get(Calendar.HOUR_OF_DAY)
            timePicker.currentMinute = now.get(Calendar.MINUTE)
        }

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
        TimerConfiguration.getInstance().setListRepeatTimerType(this,repeatType)
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

    override fun onBackPressed() {
        backButtonPress()
        super.onBackPressed()
    }
}
