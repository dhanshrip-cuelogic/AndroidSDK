package com.loner.android.sdk.countdowntimer

import android.os.CountDownTimer

class MonitoringCoutDownTimer: CountDownTimer {
    var checkInTimerListener: CheckInTimerListener

    constructor(millisInFuture: Long, countDownInterval: Long, checkInTimerListener: CheckInTimerListener) : super(millisInFuture, countDownInterval){
        this.checkInTimerListener = checkInTimerListener
    }

    override fun onFinish() {
        checkInTimerListener.onMonitorTimerFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        checkInTimerListener.onViewUpdateOnTimer(millisUntilFinished)
    }



}