package com.loner.android.sdk.activity


import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.NumberPicker
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.TimerListener
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.data.sdkconfiguraton.AppConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerDataStore
import com.loner.android.sdk.utils.TimerValidation
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_set_timer.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


class SetTimerActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mHourPicker: NumberPicker
    private lateinit var mMinutePicker: NumberPicker
    private lateinit var mBackImageView: ImageView
    private var scrollingHours = false
    private var scrollingMinutes = false
    private var displayMinuteValue = arrayOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
            "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59")
    private var mRepeatType = " "
    private var mSpecificTime = " "
    private  var timerListener: TimerListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTimerActivityInstance(this)
        setContentView(R.layout.activity_set_timer)
        timerListener = CheckInTimerView.getCheckInTimerView()
        initUI()
        initConfigurationValue()
        mHourPicker.maxValue = 4
        mHourPicker.minValue = 0
        mMinutePicker.maxValue = 59
        mMinutePicker.minValue = 0
        mMinutePicker.displayedValues = displayMinuteValue
        setDividerColor(mHourPicker, ContextCompat.getColor(this, R.color.timer_colour))
        setDividerColor(mMinutePicker,ContextCompat.getColor(this, R.color.timer_colour))
        if (!TimerConfiguration.getInstance().isTimerEnable(this) && !AppConfiguration.getInstance().isTimerManualCheckInEnable(this)) {
            btnDisableTimer.setTextColor(ContextCompat.getColor(this, R.color.disable_button_colour))
            btnDisableTimer.isEnabled = false
        }

        try {
            if (TimerConfiguration.getInstance().isTimerEnableEnabled(this)) {
                mHourPicker.value = TimerValidation.getHours(AppConfiguration.getInstance().getTimeBetweenCheckInLng(this))
                mMinutePicker.value = TimerValidation.getMinutes(AppConfiguration.getInstance().getTimeBetweenCheckInLng(this))
                validationHourPickerLabel(TimerValidation.getHours(AppConfiguration.getInstance().getTimeBetweenCheckInLng(this)))
                validationMinutesLabelPicker(TimerValidation.getMinutes(AppConfiguration.getInstance().getTimeBetweenCheckInLng(this)))
            } else {
                mHourPicker.value = 1
                mMinutePicker.value = 0
                validationHourPickerLabel(1)
                validationMinutesLabelPicker(0)
            }
        } catch (E: Exception) {
            mHourPicker.value = 1
            mMinutePicker.value = 0
            validationHourPickerLabel(1)
            validationMinutesLabelPicker(0)
        }
        if (TimerConfiguration.getInstance().getRepeatTimerType(this) == getText(R.string.until_a_specific_item))
            textrepeatselection.text = getText(R.string.until_specific).toString() + " " + TimerValidation.getDateTimeAsPerLanguage(TimerConfiguration.getInstance().getSpecificTimeCheckIn(this))
        else
            textrepeatselection.text = TimerConfiguration.getInstance().getRepeatTimerType(this)


        mHourPicker.setOnScrollListener { view, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                scrollingHours = false
                validationHourPicker(mHourPicker.value)

            } else {
                scrollingHours = true
            }
        }
        mHourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (!scrollingHours)
                validationHourPicker(newVal)
        }

        mMinutePicker.setOnScrollListener { view, scrollState ->
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                scrollingMinutes = false
                validationMinutePicker(mMinutePicker.value)

            } else {
                scrollingMinutes = true
            }
        }
        mMinutePicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (!scrollingMinutes)
                validationMinutePicker(newVal)
        }

    }

    private fun initConfigurationValue() {
        mRepeatType = TimerConfiguration.getInstance().getRepeatTimerType(this)
        if (mRepeatType == getText(R.string.until_a_specific_item)) {
            mSpecificTime = TimerConfiguration.getInstance().getSpecificTimeCheckIn(this)
            TimerConfiguration.getInstance().setListHourTimerPicker(this, TimerConfiguration.getInstance().getHourTimerPicker(this))
            TimerConfiguration.getInstance().setListMinuteTimerPicker(this, TimerConfiguration.getInstance().getMinuteTimerPicker(this))
            TimerConfiguration.getInstance().setListYearOfDatePicker(this, TimerConfiguration.getInstance().getYearOfDatePicker(this))
            TimerConfiguration.getInstance().setListMonthOfDatePicker(this, TimerConfiguration.getInstance().getMonthOfDatePicker(this))
            TimerConfiguration.getInstance().setListDayOfDatePicker(this, TimerConfiguration.getInstance().getDayOfDatePicker(this))
        }
        TimerConfiguration.getInstance().setListRepeatTimerType(this, TimerConfiguration.getInstance().getRepeatTimerType(this))
    }

    private fun validationMinutePicker(newValue: Int) {
        if (mHourPicker.value == 4 && newValue != 0) {
            mHourPicker.value = 2
            changeValueByOne(mHourPicker, true)
        }
        if (mHourPicker.value == 0 && newValue == 0) {
            mHourPicker.value = 0
            changeValueByOne(mHourPicker, true)
        }
        validationMinutesLabelPicker(newValue)
    }

    private fun validationMinutesLabelPicker(newValue: Int) {
        if (newValue == 0 || newValue == 1) {
            textminutes.text = getText(R.string.minute_text)
        } else {
            textminutes.text = getText(R.string.minutes_text)
        }
    }

    private fun validationHourPicker(newValue: Int) {
        if (newValue == 4 && mMinutePicker.value != 0) {
            mMinutePicker.value = 59
            changeValueByOne(mMinutePicker, true)
        }
        if (newValue == 0 && mMinutePicker.value == 0) {
            mMinutePicker.value = 0
            changeValueByOne(mMinutePicker, true)
        }
        validationHourPickerLabel(newValue)
    }

    private fun validationHourPickerLabel(newValue: Int) {
        if (newValue == 0 || newValue == 1) {
            texthour.text = getText(R.string.hour_text)
        } else {
            texthour.text = getText(R.string.hours_text)
        }
    }

    private fun changeValueByOne(higherPicker: NumberPicker, increment: Boolean) {
        val method: Method
        try {
            method = higherPicker.javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(higherPicker, increment)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

    }

    private fun initUI() {
        mHourPicker = findViewById(R.id.hourPicker)
        mMinutePicker = findViewById(R.id.minutePicker)
        mBackImageView = findViewById(R.id.backButton)
        repeatLayout.setOnClickListener(this)
        btnDisableTimer.setOnClickListener(this)
        btnSaveTimer.setOnClickListener(this)
        btnStop.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.repeatLayout -> {
                val intent = Intent(this, SetRepeatActivity::class.java)
                startActivityForResult(intent, TIMER_REPEAT_REQUEST)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            R.id.btnStop -> {
                if (TimerConfiguration.getInstance().isTimerEnable(this)) {
                    TimerConfiguration.getInstance().setListRepeatTimerType(this, TimerConfiguration.getInstance().getRepeatTimerType(this))

                } else {
                    TimerDataStore.getInstance(this).clearAll()
                }
                this@SetTimerActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.btnDisableTimer -> {
                timerListener?.disableTimer()
                TimerDataStore.getInstance(this).clearAll()
                this@SetTimerActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.btnSaveTimer -> {
                if (mRepeatType != getText(R.string.until_a_specific_item)) {
                    saveTimerConfiguration()
                } else if (mRepeatType == getText(R.string.until_a_specific_item)) {
                    if (TimerValidation.isCurrentTimeSame(mSpecificTime)) {
                        saveTimerConfiguration()
                    } else {
                          LonerDialog.getInstance().showAlertDialog(this@SetTimerActivity, null, getText(R.string.check_in_time_current).toString(), getText(R.string.okay).toString(),null)
                    }
                }
            }

        }
    }

    private fun saveTimerConfiguration() {
        AppConfiguration.getInstance().setTimeBetweenCheckinLng(this, TimerValidation.checkInTimerValue(mHourPicker.value, mMinutePicker.value))
        TimerConfiguration.getInstance().setTimerEnable(this, true)
        TimerConfiguration.getInstance().setTimerEnabled(this, true)
        TimerConfiguration.getInstance().setRepeatTimerType(this, mRepeatType)
        if (mRepeatType == getText(R.string.until_a_specific_item)) {
            TimerConfiguration.getInstance().setSpecificTimeCheckIn(this, mSpecificTime)
            TimerConfiguration.getInstance().setHourTimerPicker(this, TimerConfiguration.getInstance().getListHourTimerPicker(this))
            TimerConfiguration.getInstance().setMinuteTimerPicker(this, TimerConfiguration.getInstance().getListMinuteTimerPicker(this))
            TimerConfiguration.getInstance().setYearOfDatePicker(this, TimerConfiguration.getInstance().getListYearOfDatePicker(this))
            TimerConfiguration.getInstance().setDayOfDatePicker(this, TimerConfiguration.getInstance().getListDayOfDatePicker(this))
            TimerConfiguration.getInstance().setMonthOfDatePicker(this, TimerConfiguration.getInstance().getListMonthOfDatePicker(this))
        } else {
            TimerConfiguration.getInstance().setSpecificTimeCheckIn(this, " ")
            mSpecificTime = " "
        }
        timerListener?.setNewTimer()
        TimerConfiguration.getInstance().setListRepeatTimerType(this, mRepeatType)
        this@SetTimerActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    companion object {
        private const val TIMER_REPEAT_REQUEST = 101
        private  var instance: SetTimerActivity? = null
       private fun setTimerActivityInstance(instance: SetTimerActivity?) {
            this.instance = instance
        }

        fun getTimerActivityInstance(): SetTimerActivity? {
            return instance?: null
        }
    }

    private fun setDividerColor(picker: NumberPicker, color: Int) {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(color)
                    pf.set(picker, colorDrawable)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
    }


    override fun onBackPressed() {
        if (TimerConfiguration.getInstance().isTimerEnable(this)) {
            TimerConfiguration.getInstance().setListRepeatTimerType(this, TimerConfiguration.getInstance().getRepeatTimerType(this))

        } else {
            TimerDataStore.getInstance(this).clearAll()
        }
        this@SetTimerActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 105) {
            mRepeatType = data!!.getStringExtra("timer_repeat_type")
            if (mRepeatType == getText(R.string.until_a_specific_item)) {
                mSpecificTime = data.getStringExtra("until_specific_time")
                textrepeatselection.text = getText(R.string.until_specific).toString() + " " + TimerValidation.getDateTimeAsPerLanguage(mSpecificTime)
            } else {
                textrepeatselection.text = TimerConfiguration.getInstance().getListRepeatTimerType(this)
            }
        }
    }
    override fun onNetworkConnected() {
        super.onNetworkConnected()
        repeatLayout.isEnabled = true
        btnDisableTimer.isEnabled = true
        btnSaveTimer.isEnabled = true
    }

    override fun onNetworkDisconnected() {
        super.onNetworkDisconnected()
        repeatLayout.isEnabled = false
        btnDisableTimer.isEnabled = false
        btnSaveTimer.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        setTimerActivityInstance(null)
    }
}
