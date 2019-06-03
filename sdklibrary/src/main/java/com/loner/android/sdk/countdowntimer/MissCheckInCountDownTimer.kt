package com.loner.android.sdk.countdowntimer

import android.os.CountDownTimer

class MissCheckInCountDownTimer:CountDownTimer {
    private  var missCheckInTimerListener:MissCheckInTimerListener

    constructor(millisInFuture: Long, countDownInterval: Long, missCheckInTimerListener: MissCheckInTimerListener) : super(millisInFuture, countDownInterval){
        this.missCheckInTimerListener = missCheckInTimerListener
    }

    override fun onFinish() {
        missCheckInTimerListener.onCountDownFinish()
    }

    override fun onTick(millisUntilFinished: Long) {

    }
}