package com.loner.android.sdk.widget

import com.loner.android.sdk.countdowntimer.CheckInTimerListener
import com.loner.android.sdk.countdowntimer.MonitoringCountDownTimer
import com.loner.android.sdk.utils.convertMinuteToSecond
import com.loner.android.sdk.utils.convertSecondToMillisecond

class CheckInTimerPresenter(view: View) : CheckInTimerListener {
    private val viewListener = view
    private var timerState = TimerState.Stopped
    private var monitorTimerLengthMillisecond: Long = 0
    private var currentTimerLengthMillisecond: Long = 0
    private var monitoringTimer: MonitoringCountDownTimer? = null
    fun monitoringStart(isAllowUserToConfigure: Boolean, isManualCheckInEnabled: Boolean, timerValue: Int) {
        checkVisibilityOfView(isAllowUserToConfigure, isManualCheckInEnabled)
        if (timerValue != 0 && isManualCheckInEnabled) {
            val checkTimeInSecond = timerValue.convertSecondToMillisecond()
            viewListener.monitorStart(checkTimeInSecond)
            monitorTimerLengthMillisecond = checkTimeInSecond.convertMinuteToSecond()
            startMonitorTimer()
        }

    }

    private fun checkVisibilityOfView(isAllowUserToConfigure: Boolean, isManualCheckInEnabled: Boolean) {
        if (isManualCheckInEnabled && !isAllowUserToConfigure) {
            viewListener.manualCheckInOnAndAllowUserOff()
        } else if (isManualCheckInEnabled) {
            viewListener.manualCheckInAndAllowUserOn()
        } else if (!isManualCheckInEnabled && !isAllowUserToConfigure) {
            viewListener.manualCheckInAndAllowUserOff()
        } else {
            viewListener.manualCheckInOffAndAllowUserOn()
        }
    }

    interface View {
        fun manualCheckInOnAndAllowUserOff()
        fun manualCheckInAndAllowUserOn()
        fun manualCheckInAndAllowUserOff()
        fun manualCheckInOffAndAllowUserOn()
        fun monitorStart(checkTimeSecond: Long)
        fun onViewUpdateOnTimer(millisUntilFinished: Long)
        fun onMonitorTimerFinish()
        fun timerStateChange(isState: Boolean)
    }

    fun startMonitorTimer() {
        if (getTimerStatus() == TimerState.Running) stopMonitorTimer()
        val timerValue = if (getTimerStatus() == TimerState.Paused)
            currentTimerLengthMillisecond
        else
            monitorTimerLengthMillisecond
        monitoringTimer = MonitoringCountDownTimer(timerValue, 1000, this)
        monitoringTimer!!.start()
        setTimerStatus(TimerState.Running)
        viewListener.timerStateChange(true)
    }

    fun pausedMonitorTimer() {
        monitoringTimer?.cancel()
        monitoringTimer = null
        setTimerStatus(TimerState.Paused)

    }

    fun stopMonitorTimer() {
        monitoringTimer?.cancel()
        monitoringTimer = null
        setTimerStatus(TimerState.Stopped)
        viewListener.timerStateChange(false)

    }


    private fun setTimerStatus(timerState: TimerState) {
        this.timerState = timerState
    }

    private fun getTimerStatus(): TimerState {
        return timerState
    }


    override fun onMonitorTimerFinish() {
        viewListener.onMonitorTimerFinish()
    }

    override fun onViewUpdateOnTimer(millisUntilFinished: Long) {
        viewListener.onViewUpdateOnTimer(millisUntilFinished)
    }


    fun setNewTimer(timeBetweenCheckInLng: Int) {
        monitorTimerLengthMillisecond = timeBetweenCheckInLng.convertSecondToMillisecond()

    }

    fun setCurrentTimeInMilisecond(millisUntilFinished: Long) {
        currentTimerLengthMillisecond = millisUntilFinished
    }


    /*Timer implementation*/
    enum class TimerState {
        Stopped, Paused, Running
    }

}