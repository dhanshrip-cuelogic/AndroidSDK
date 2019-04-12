package com.loner.android.sdk.utils


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utility {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    /**
     * gets the current date
     *
     * @return String formated date
     */
    val currentDate: String
        get() {
            val date = Date()
            return getFormattedDate(date)
        }

    /**
     * Returns Utc formated date
     *
     * @return String formated date
     */
    fun getFormattedDate(d: Date): String {
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(d) + ""
    }
}
