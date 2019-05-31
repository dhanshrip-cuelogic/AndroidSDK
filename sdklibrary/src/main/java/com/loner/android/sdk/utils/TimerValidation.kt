package com.loner.android.sdk.utils



import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class  TimerValidation {

    companion object {
        val MONTHS = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        private val dateTimeFormat = SimpleDateFormat("dd MMM yy hh:mm a", Locale.ENGLISH)
        private val dateTimeFormatLocal = SimpleDateFormat("dd MMM yy hh:mm a")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        private val currentDate = Date()
        private val calendar = Calendar.getInstance(Locale.US)

        val year: Int
            get() = calendar.get(Calendar.YEAR)

        val month: Int
            get() = calendar.get(Calendar.MONTH)

        val dayOfMonth: Int
            get() = calendar.get(Calendar.DAY_OF_MONTH)
        val currentTimeMillies: Long
            get() = calendar.timeInMillis

        fun getCheckTime(seconds: Long): Int {
            return (seconds / 60).toInt()
        }

        fun getHours(seconds: Long): Int {
            return getCheckTime(seconds) / 60
        }

        fun getMinutes(seconds: Long): Int {
            return getCheckTime(seconds) % 60
        }

        fun checkInTimerValue(hours: Int, minutes: Int): Long {
            var mCheckInTime: Long = 0
            try {
                if (hours != 0 && minutes != 0) {
                    mCheckInTime = ((hours * 60 + minutes) * 60).toLong()
                } else if (hours != 0) {
                    mCheckInTime = (hours * 60 * 60).toLong()
                } else if (minutes != 0) {
                    mCheckInTime = (minutes * 60).toLong()
                } else {
                    mCheckInTime = (1 * 60).toLong()
                }
            } catch (e: Exception) {
                mCheckInTime = (1 * 60).toLong()
            }

            return mCheckInTime
        }

        fun checkInTimerMinuteValue(hours: Int, minutes: Int): Long {
            var mCheckInTime: Long = 0
            try {
                if (hours != 0 && minutes != 0) {
                    mCheckInTime = (hours * 60 + minutes).toLong()
                } else if (hours != 0) {
                    mCheckInTime = (hours * 60).toLong()
                } else if (minutes != 0) {
                    mCheckInTime = minutes.toLong()
                } else {
                    mCheckInTime = 30
                }
            } catch (e: Exception) {
                mCheckInTime = 30
            }

            return mCheckInTime
        }

        fun getTimePickerTime(hours: Int, mins: Int, year: Int, month: Int, day: Int): String {
            var hours = hours
            var timeSet = ""
            var minutes = ""
            var aTime = " "
            try {
                if (hours > 12) {
                    hours -= 12
                    timeSet = "PM"
                } else if (hours == 0) {
                    hours += 12
                    timeSet = "AM"
                } else if (hours == 12)
                    timeSet = "PM"
                else
                    timeSet = "AM"

                if (mins < 10)
                    minutes = "0$mins"
                else
                    minutes = mins.toString()
                // Append in a StringBuilder
                val mMonth = MONTHS[month]
                aTime = StringBuilder().append(day).append(" ").append(mMonth).append(" ").append(year).append(" ").append(hours).append(':')
                        .append(minutes).append(" ").append(timeSet).toString()
                val date = dateTimeFormat.parse(aTime)
                aTime = dateTimeFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return aTime
        }

        fun getMinuteDifferenceTime(timerPickerValue: String): Int {
            var mMinute = 0
            try {
                val calendar = Calendar.getInstance()
                val time = dateTimeFormat.format(calendar.time)
                val date1 = dateTimeFormat.parse(time)
                val date2 = dateTimeFormat.parse(timerPickerValue)
                mMinute = ((date2.time - date1.time) / 1000 / 60).toInt()
            } catch (e: Exception) {

            }

            return mMinute
        }

        fun IsCurrentTimeSame(timeValue: String): Boolean {
            var isCurrent = false
            if (0 >= getMinuteDifferenceTime(timeValue)) {
                isCurrent = false
            } else {
                isCurrent = true
            }
            return isCurrent
        }

        fun getDateTimeAsPerLanguage(datTime: String): String {
            return dateTimeFormatLocal.format(Date(datTime))
        }
    }

}
