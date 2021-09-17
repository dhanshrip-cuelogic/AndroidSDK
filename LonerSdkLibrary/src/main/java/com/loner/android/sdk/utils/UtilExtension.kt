package com.loner.android.sdk.utils

import java.util.concurrent.TimeUnit


fun convertCheckInTextFormat(millisUntilFinished: Long): String {
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
}

fun Int.convertSecondToMillisecond() : Long {
    return (this*1000).toLong()
}
fun Long.convertMinuteToSecond() : Long {
    return this*60
}

