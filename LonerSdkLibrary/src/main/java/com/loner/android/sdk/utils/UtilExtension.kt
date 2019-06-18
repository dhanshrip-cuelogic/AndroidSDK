package com.loner.android.sdk.utils

import com.loner.android.sdk.widget.CheckInTimerView
import java.util.concurrent.TimeUnit


fun CheckInTimerView.convertCheckInTextFormat(millisUntilFinished: Long): String {
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
}

fun CheckInTimerView.convertSecondToMillisecond(seconds: Long) : Long {
    return seconds*1000
}
fun CheckInTimerView.convertMinuteToSecond(seconds: Int) : Long {
    return (seconds*60).toLong()
}