package com.loner.android.sdk.countdowntimer

import android.os.CountDownTimer

class MonitoringCountDownTimer(millisInFuture: Long, countDownInterval: Long, private var checkInTimerListener: CheckInTimerListener) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onFinish() {
        checkInTimerListener.onMonitorTimerFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        checkInTimerListener.onViewUpdateOnTimer(millisUntilFinished)
    }



}