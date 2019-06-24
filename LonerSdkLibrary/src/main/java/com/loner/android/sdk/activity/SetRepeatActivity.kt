package com.loner.android.sdk.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TimePicker
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.RepeatTimerListener
import com.loner.android.sdk.activity.adapter.RepeatTimerAdapter
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.utils.TimerValidation
import kotlinx.android.synthetic.main.activity_set_repeation.*
import java.util.*

class SetRepeatActivity : BaseActivity(), DatePicker.OnDateChangedListener, RepeatTimerListener {
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
            backButtonPress()
        }
        if (TimerConfiguration.getInstance().getListRepeatTimerType(this) == getText(R.string.until_a_specific_item)) {
            timePicker.setTime(TimerConfiguration.getInstance().getListHourTimerPicker(this),TimerConfiguration.getInstance().getListMinuteTimerPicker(this))
            val day = TimerConfiguration.getInstance().getListDayOfDatePicker(this)
            val month = TimerConfiguration.getInstance().getListMonthOfDatePicker(this)
            val year = TimerConfiguration.getInstance().getListYearOfDatePicker(this)
            datePicker.init(year, month, day, this)
        } else {
            now = Calendar.getInstance(Locale.ENGLISH)
            timePicker.setTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE))
            datePicker.init(TimerValidation.year, TimerValidation.month, TimerValidation.dayOfMonth, this)

        }

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            if (0 > TimerValidation.getMinuteDifferenceTime(TimerValidation.getTimePickerTime(hourOfDay, minute, datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
                now = Calendar.getInstance()
                timePicker.setTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE))
            }
        }

    }

    private fun backButtonPress() {
        val backIntent = Intent()
        if (TimerConfiguration.getInstance().getListRepeatTimerType(this) == getText(R.string.until_a_specific_item)) {
            if (TimerValidation.isCurrentTimeSame(TimerValidation.getTimePickerTime(timePicker.getCurrentPickerHour(), timePicker.getCurrentPickerMinute(), datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
                backIntent.putExtra("until_specific_time", TimerValidation.getTimePickerTime(timePicker.getCurrentPickerHour(), timePicker.getCurrentPickerMinute(), datePicker.year, datePicker.month, datePicker.dayOfMonth))
                TimerConfiguration.getInstance().setListHourTimerPicker(this, timePicker.getCurrentPickerHour())
                TimerConfiguration.getInstance().setListMinuteTimerPicker(this, timePicker.getCurrentPickerMinute())
                TimerConfiguration.getInstance().setListDayOfDatePicker(this, datePicker.dayOfMonth)
                TimerConfiguration.getInstance().setListMonthOfDatePicker(this, datePicker.month)
                TimerConfiguration.getInstance().setListYearOfDatePicker(this, datePicker.year)
                backIntent.putExtra("timer_repeat_type", TimerConfiguration.getInstance().getListRepeatTimerType(this))
                setResult(105, backIntent)
                this@SetRepeatActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            } else {
                LonerDialog.getInstance().showAlertDialog(this@SetRepeatActivity, null, getText(R.string.check_in_time_current).toString(), getText(R.string.okay).toString())
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

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (0 > TimerValidation.getMinuteDifferenceTime(TimerValidation.getTimePickerTime(timePicker.getCurrentPickerHour(), timePicker.getCurrentPickerMinute(), datePicker.year, datePicker.month, datePicker.dayOfMonth))) {
            now = Calendar.getInstance()
            timePicker.setTime(now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE))
        }

    }

    companion object {
        private  var instance: SetRepeatActivity? = null
        fun setRepeatActivityInstance(instance: SetRepeatActivity?) {
            this.instance = instance
        }

        fun getRepeatActivityInstance(): SetRepeatActivity? {
            return instance?: null
        }
    }
    override fun setRepeat(repeatType: String) {
        TimerConfiguration.getInstance().setListRepeatTimerType(this, repeatType)
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
    override fun onNetworkConnected() {
        super.onNetworkConnected()
        btnBackButton.isEnabled = true
    }

    override fun onNetworkDisconnected() {
        super.onNetworkDisconnected()
        btnBackButton.isEnabled = false
    }

   private fun TimePicker.setTime(hour:Int, minute:Int){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           timePicker.hour = hour
           timePicker.minute = minute
       } else {
           timePicker.currentHour = hour
           timePicker.currentMinute = minute
       }
   }
  private fun TimePicker.getCurrentPickerHour():Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.hour else timePicker.currentHour
  }
    private fun TimePicker.getCurrentPickerMinute():Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.minute else timePicker.currentMinute
    }
}
