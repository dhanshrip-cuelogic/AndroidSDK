package com.loner.android.sdk.data.timerconfiguration

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class TimerDataStore private constructor(context: Context) {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val TIMER_REPEAT_SHARED = "timer_preference"

    init {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(TIMER_REPEAT_SHARED, Activity.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
        }
    }
    companion object {
        const val LIST_TIMER_REPEAT_TYPE = "list_repeat_type"
        const val TIMER_REPEAT_TYPE = "repeat_type"
        const val TIMER_HOUR_PICKER = "hour_picker_value"
        const val LIST_TIMER_HOUR_PICKER = "list_hour_picker_value"
        const val TIMER_MINUTE_PICKER = "minute_picker_value"
        const val LIST_TIMER_MINUTE_PICKER = "list_minute_picker_value"
        const val DAY_DATE_PICKER = "day_date_picker"
        const val MONTH_DATE_PICKER = "month_date_picker"
        const val YEAR_DATE_PICKER = "year_date_picker"
        const val LIST_DAY_DATE_PICKER = "list_day_date_picker"
        const val LIST_MONTH_DATE_PICKER = "list_month_date_picker"
        const val LIST_YEAR_DATE_PICKER = "list_year_date_picker"
        const val SET_TIMER_FIRST_TIME = "set_timer_first_time"
        const val TIMER_SET = "timer_enable"
        const val TIMER_SPECIFIC_TIME = "specific_time_to_check_in"
        @Volatile
        private var sharePrefInstance: TimerDataStore? = null
        fun getInstance(context: Context): TimerDataStore {
            return sharePrefInstance ?: synchronized(this) {
                TimerDataStore(context).also {
                    sharePrefInstance = it
                }
            }
        }
    }

    fun clearAll() {
        editor!!.clear()
        editor!!.commit()
    }

    fun saveStringData(variable: String, data: String) {
        editor!!.putString(variable, data)
        editor!!.commit()
    }

    fun saveIntData(variable: String, data: Int) {
        editor!!.putInt(variable, data)
        editor!!.commit()
    }

    /**
     * get the string data from the shared preferences in accordance with the
     * attribute pass to the function
     * @param variable String
     * @param defaultValue String
     * @return String value
     */
    fun getStringData(variable: String, defaultValue: String): String {
        return sharedPreferences!!.getString(variable, defaultValue)
    }

    fun getIntData(variable: String, defaultValue: Int): Int {
        return sharedPreferences!!.getInt(variable, defaultValue)
    }

    fun saveBooleanData(variable: String, data: Boolean) {
        editor!!.putBoolean(variable, data)
        editor!!.commit()
    }

    fun getBooleanData(variable: String, defaultValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(variable, defaultValue)
    }
}