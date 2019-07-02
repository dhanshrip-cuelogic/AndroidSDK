package com.loner.android.sdk.utils

import java.util.concurrent.TimeUnit


fun convertCheckInTextFormat(millisUntilFinished: Long): String {
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
}

fun convertSecondToMillisecond(seconds: Long) : Long {
    return seconds*1000
}
fun convertMinuteToSecond(seconds: Int) : Long {
    return (seconds*60).toLong()
}

