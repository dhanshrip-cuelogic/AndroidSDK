package com.loner.android.sdk.countdowntimer

import android.os.CountDownTimer

class MissCheckInCountDownTimer(millisInFuture: Long, countDownInterval: Long, private var missCheckInTimerListener: MissCheckInTimerListener) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onFinish() {
        missCheckInTimerListener.onCountDownFinish()
    }

    override fun onTick(millisUntilFinished: Long) {

    }
}