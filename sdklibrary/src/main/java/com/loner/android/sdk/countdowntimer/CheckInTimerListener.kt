package com.loner.android.sdk.countdowntimer



interface CheckInTimerListener{
    fun onMonitorTimerFinish()
    fun onViewUpdateOnTimer(millisUntilFinished: Long)


}