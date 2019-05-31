package com.loner.android.sdk.data.timerconfiguration

import android.content.Context
import com.loner.android.sdk.R


class TimerConfiguration private constructor(){

    companion object {
        @Volatile
        private var timerConfigurationInstance: TimerConfiguration? = null
        fun getInstance(): TimerConfiguration {
            return timerConfigurationInstance ?: synchronized(this) {
                TimerConfiguration().also {
                    timerConfigurationInstance = it
                }
            }
        }
    }

    fun setListRepeatTimerType(context: Context,value: String) {
        TimerDataStore.getInstance(context).saveStringData(TimerDataStore.LIST_TIMER_REPEAT_TYPE, value)
    }
    fun getListRepeatTimerType(context: Context): String {
        return TimerDataStore.getInstance(context).getStringData(TimerDataStore.LIST_TIMER_REPEAT_TYPE, context.getText(R.string.never).toString())
    }

    fun setRepeatTimerType(context: Context,value: String) {
        TimerDataStore.getInstance(context).saveStringData(TimerDataStore.TIMER_REPEAT_TYPE, value)
    }

    fun getRepeatTimerType(context: Context): String {
        return TimerDataStore.getInstance(context).getStringData(TimerDataStore.TIMER_REPEAT_TYPE, context.getText(R.string.never).toString())
    }

    fun setHourTimerPicker(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.TIMER_HOUR_PICKER, value)
    }

    fun getHourTimerPicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.TIMER_HOUR_PICKER, 1)
    }

    fun setListHourTimerPicker(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.LIST_TIMER_HOUR_PICKER, value)
    }
    fun getListHourTimerPicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.LIST_TIMER_HOUR_PICKER, 1)
    }
    fun setMinuteTimerPicker(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.TIMER_MINUTE_PICKER, value)
    }
    fun getMinuteTimerPicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.TIMER_MINUTE_PICKER, 0)
    }

    fun setListMinuteTimerPicker(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.LIST_TIMER_MINUTE_PICKER, value)
    }

    fun getListMinuteTimerPicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.LIST_TIMER_MINUTE_PICKER, 0)
    }

    fun setDayofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.DAY_DATE_PICKER, value)
    }
    fun getDayofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.DAY_DATE_PICKER, 0)
    }
    fun setListDayofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.LIST_DAY_DATE_PICKER, value)
    }
    fun getListDayofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.LIST_DAY_DATE_PICKER, 0)
    }

    fun setMonthofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.MONTH_DATE_PICKER, value)
    }
    fun getMonthofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.MONTH_DATE_PICKER, 0)
    }
    fun setListMonthofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.LIST_MONTH_DATE_PICKER, value)
    }

    fun getListMonthofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.LIST_MONTH_DATE_PICKER, 0)
    }
    fun setYearofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.YEAR_DATE_PICKER, value)
    }

    fun getYearofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.YEAR_DATE_PICKER, 0)
    }

    fun setListYearofDatePicekr(context: Context,value: Int) {
        TimerDataStore.getInstance(context).saveIntData(TimerDataStore.LIST_YEAR_DATE_PICKER, value)
    }


    fun getListYearofDatePicker(context: Context): Int {
        return TimerDataStore.getInstance(context).getIntData(TimerDataStore.LIST_YEAR_DATE_PICKER, 0)
    }

    fun isTimerEnable(context: Context): Boolean {
        return TimerDataStore.getInstance(context).getBooleanData(TimerDataStore.TIMER_SET, false)
    }
    fun setTimerEnable(context: Context,isEnable: Boolean) {
        TimerDataStore.getInstance(context).saveBooleanData(TimerDataStore.TIMER_SET, isEnable)
    }

    fun setSpecficTimeCheckIn(context: Context,timeCheckIn: String) {
        TimerDataStore.getInstance(context).saveStringData(TimerDataStore.TIMER_SPECFIC_TIME, timeCheckIn)
    }

    fun getSpecficTimeCheckIn(context: Context): String {
        return TimerDataStore.getInstance(context).getStringData(TimerDataStore.TIMER_SPECFIC_TIME, " ")
    }
    fun isTimerEnableEnabled(context: Context): Boolean {
        return TimerDataStore.getInstance(context).getBooleanData(TimerDataStore.SET_TIMER_FIRST_TIME, false)
    }
    fun setTimerEnabled(context: Context,isTimerFirstTime: Boolean) {
        TimerDataStore.getInstance(context).saveBooleanData(TimerDataStore.SET_TIMER_FIRST_TIME, isTimerFirstTime)

    }
}