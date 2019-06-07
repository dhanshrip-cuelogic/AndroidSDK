package com.loner.android.sdk.widget

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.activity.ActivityInterface.TimerListener
import com.loner.android.sdk.activity.CheckInActivity
import com.loner.android.sdk.activity.MissedCheckInActivity
import com.loner.android.sdk.activity.SetTimerActivity
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.countdowntimer.CheckInTimerListener
import com.loner.android.sdk.countdowntimer.MonitoringCoutDownTimer
import com.loner.android.sdk.data.sdkconfiguraton.AppConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerDataStore
import com.loner.android.sdk.utils.*
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface


class CheckInTimerView: RelativeLayout, CheckInTimerListener,ManualCheckInListener,TimerListener,CheckViewUpdateListener {
    private var mCheckInTimerDisable: TextView? = null
    private var mCheckInTimerDiscription: TextView? = null
    private var mCheckInTimer: TextView? = null
    private var mSetTimerButton: Button? = null
    private var mMunalCheckInButton: Button? = null
    private var mContext: Context? = null
    private var mTimerCount = 0
    private var isTimerEnable = false
    private var mTimerListener:OnTimerListener? = null
    /*Timer implementation*/
    enum class TimerState {
        Stopped, Paused, Running
    }
    enum class TimerCount {
        TimerSpecficTime,
        TimerNone
    }
    enum class TimerType{
        TimerTypeNever,
        TimerTypeUntilTurnedOff,
        TimerTypeOnce,
        TimerTypeTwice,
        TimerTypeUntilSpecificTime,
        TimerTypeNone
    }

    private var timerType = TimerType.TimerTypeNone
    private var timerSpecificType = TimerCount.TimerNone
    private var timerState = TimerState.Stopped
    private var monitorTimerLengthMillisecond: Long = 0
    private var currentTimerLengthMillisecond: Long = 0
    private var monitoringTimer: MonitoringCoutDownTimer? = null

    companion object {
        lateinit var instance : CheckInTimerView

        fun setCheckInTimerView(instance:CheckInTimerView){
            this.instance = instance
        }

        fun getCheckInTimerView():CheckInTimerView{
            return instance
        }
    }

    constructor(context: Context) : super(context) {
        this.mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.check_in_view, this)
        setCheckInTimerView(this)
        mCheckInTimerDisable = findViewById(R.id.txtManualDisabled)
        mCheckInTimerDiscription = findViewById(R.id.txtCheckin)
        mCheckInTimer = findViewById(R.id.txtTimer)
        mSetTimerButton = findViewById(R.id.btnSetTimerButton)
        mMunalCheckInButton = findViewById(R.id.btnCheckinWithNotes)
        TimerDataStore.getInstance(mContext!!).clearAll()
        mSetTimerButton!!.setOnClickListener {
            var intent = Intent(mContext, SetTimerActivity::class.java)
            mContext?.startActivity(intent)
        }
        mMunalCheckInButton!!.setOnClickListener {
            var intent = Intent(mContext, CheckInActivity::class.java)
            mContext?.startActivity(intent)
        }
    }

    fun loadCheckInTimerComponent(isAllowUserToConfigure : Boolean?,isManualCheckInEnabled:Boolean?, timerValue : Int?,timerListener: OnTimerListener? ){
        timerListener?.let { this.mTimerListener = timerListener }

        if(isAllowUserToConfigure == null || isManualCheckInEnabled == null || timerValue == null){
            throw IllegalStateException("All value should not be null")
            return
        }
        checkVisibilityOfView(isAllowUserToConfigure, isManualCheckInEnabled)
        if(timerValue != 0){
            var checkTimeInSecond = this.ConvertMinuteToSecond(timerValue)
            AppConfiguration.getInstance().setTimeBetweenCheckinsLng(mContext!!,checkTimeInSecond)
            AppConfiguration.getInstance().setManualCheckIn(mContext!!,true)
            AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!,true)
            monitorTimerLengthMillisecond = this.ConvertSecondToMilisecond(checkTimeInSecond)
            startMonitorTimer()
        }

    }

    private fun startMonitorTimer(){
        if(getTimerStatus() == TimerState.Running) stopMonitorTimer()

        val timerValue = if (getTimerStatus() == TimerState.Paused)
            currentTimerLengthMillisecond
        else
            monitorTimerLengthMillisecond

        monitoringTimer = MonitoringCoutDownTimer(timerValue,1000,this)
        monitoringTimer!!.start()
        setTimerStatus(TimerState.Running)
    }

    private fun pausedMonitorTimer() {
        monitoringTimer?.cancel()
        monitoringTimer = null
        setTimerStatus(TimerState.Paused)

    }

    private fun stopMonitorTimer(){
        monitoringTimer?.cancel()
        monitoringTimer = null
        setTimerStatus(TimerState.Stopped)
        mCheckInTimer!!.text = "00:00:00"

    }


    private fun setTimerStatus(timerState: TimerState){
       this.timerState = timerState
    }
    private fun getTimerStatus():TimerState{
        return timerState
    }
    private fun getTimerType(): TimerType {
        return timerType
    }

    private fun setTimerType(timerType: TimerType) {
        this.timerType= timerType
    }

    private fun getTimerCount(): TimerCount {
        return timerSpecificType
    }

    private fun setTimerCount(timerCount: TimerCount) {
        this.timerSpecificType = timerCount
    }
    private fun checkVisibilityOfView(isAllowUserToConfigure : Boolean,isManualCheckInEnabled:Boolean) {
        if(isManualCheckInEnabled && !isAllowUserToConfigure){
            manualCheckInOnAndAllowUserOff()
        }else if(isManualCheckInEnabled){
            manualCheckInAndAllowUserOn()
        }else if(!isManualCheckInEnabled && !isAllowUserToConfigure) {
            manualCheckInAndAllowUserOff()
        } else {
            manualCheckInOffAndAllowUserOn()
        }
    }

    private fun manualCheckInOnAndAllowUserOff(){
        mCheckInTimerDisable?.visibility = View.GONE
        mCheckInTimerDiscription?.visibility = View.VISIBLE
        mCheckInTimerDiscription?.setText(R.string.countdown_required_to_check_in)
        mCheckInTimer?.visibility = View.VISIBLE
        mMunalCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
    }

    private fun manualCheckInAndAllowUserOn(){
        mCheckInTimerDiscription?.visibility = View.VISIBLE
        mCheckInTimerDiscription?.setText(R.string.countdown_required_to_check_in)
        mMunalCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mMunalCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mCheckInTimerDisable?.visibility = View.GONE
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!,R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!,R.color.black))
        mSetTimerButton?.setText(R.string.change_timer)
        mCheckInTimer?.visibility = View.VISIBLE
    }

    private fun manualCheckInAndAllowUserOff(){
        mCheckInTimerDiscription?.visibility = View.GONE
        mCheckInTimerDiscription?.gravity = Gravity.CENTER_HORIZONTAL
        mCheckInTimerDiscription?.setText(R.string.change_timer_desiable_decr)
        mCheckInTimerDisable?.visibility = View.VISIBLE
        mMunalCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
        mMunalCheckInButton?.setText(R.string.send_message)
        mCheckInTimer?.visibility = View.GONE
    }

    private fun manualCheckInOffAndAllowUserOn(){
        mMunalCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mCheckInTimerDisable?.visibility = View.VISIBLE
        mCheckInTimerDisable?.gravity = Gravity.LEFT
        mCheckInTimerDiscription?.visibility = View.VISIBLE
        mCheckInTimerDiscription?.setText(R.string.change_timer_desiable_decr)
        mMunalCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mSetTimerButton?.setText(R.string.set_timer)
        mMunalCheckInButton?.setText(R.string.send_message)
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!,R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!,R.color.black))
        mMunalCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mCheckInTimer?.visibility = View.GONE
    }

    override fun onMonitorTimerFinish() {
         requestForCheckIn()
         mTimerListener?.onTimerComplete()?: Loner.client.showCheckInAlertDialog(mContext!!, mContext?.getText(R.string.check_in_required).toString(),
                 mContext?.getText(R.string.press_ok_to_check_in_now).toString(),null)
    }
    override fun onCheckTimerViewUpdate() {
        acknowledgeForCheckIn()
        if (AppConfiguration.getInstance().isTimerManualCheckInEnable(mContext!!) && !TimerConfiguration.getInstance().isTimerEnable(mContext!!) && getTimerCount() === TimerCount.TimerNone && !isTimerEnable) {
            startMonitorTimer()
        } else if(!isTimerEnable()){
            clearTimer()
            AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!,false)
            TimerDataStore.getInstance(mContext!!).clearAll()
            manualCheckInOffAndAllowUserOn()
            stopMonitorTimer()
        } else if (TimerConfiguration.getInstance().isTimerEnable(mContext!!)){
            manualCheckInAndAllowUserOn()
            startMonitorTimer()
        }
    }

    private fun acknowledgeForCheckIn() {
      Loner.client.sendMessage(mContext!!,"Manual Check-in completed",object:ActivityCallBackInterface{
          override fun onResponseDataSuccess(successResponse: String) {

          }

          override fun onResponseDataFailure(failureResponse: String) {

          }
      })
    }

    override fun onMissCheckInAlert() {
        sendMissedCheckInAlert()
        var intent = Intent(mContext, MissedCheckInActivity::class.java)
        mContext?.startActivity(intent)
    }

    private fun sendMissedCheckInAlert() {
       Loner.client.sendAlertApi(mContext!!,"missed_check_in", object:ActivityCallBackInterface{
           override fun onResponseDataSuccess(successResponse: String) {

           }
           override fun onResponseDataFailure(failureResponse: String) {

           }
       })
    }

    private fun requestForCheckIn() {
        mCheckInTimer!!.text = "00:00:00"
        mTimerCount++
        Loner.client.sendNotification(mContext!!,"manual_check_in_pending",object : ActivityCallBackInterface {
            override fun onResponseDataSuccess(successResponse: String) {

            }

            override fun onResponseDataFailure(failureResponse: String) {

            }

        })
    }

    override fun onViewUpdateOnTimer(millisUntilFinished: Long) {
        if (0 >= TimerValidation.getMinuteDifferenceTime(TimerConfiguration.getInstance().getSpecficTimeCheckIn(mContext!!)) && getTimerType() === TimerType.TimerTypeUntilSpecificTime) {
            setTimerCount(TimerCount.TimerSpecficTime)
            stopMonitorTimer()
            Loner.client.showCheckInAlertDialog(mContext!!, mContext?.getText(R.string.check_in_now_to_turn_off).toString(),
                    mContext?.getText(R.string.check_in_now_to_turn_off_dec).toString(),null)

        }else {
            currentTimerLengthMillisecond = millisUntilFinished
            mCheckInTimer!!.text = this.ConvertCheckInTextFormate(millisUntilFinished)
        }
    }

    override fun manualCheckInCompeleted(timer: Boolean) {
        if(timer) {
            mTimerCount++
            stopMonitorTimer()
            startMonitorTimer()
        }

    }

    override fun setNewTimer() {
        isTimerEnable = true
        mTimerCount = -1
        setTimerTypeValue(TimerConfiguration.getInstance().getRepeatTimerType(mContext!!))
        manualCheckInAndAllowUserOn()
        monitorTimerLengthMillisecond =this.ConvertSecondToMilisecond(AppConfiguration.getInstance().getTimeBetweenCheckInLng(mContext!!))
        startMonitorTimer()
    }

    override fun disableTimer() {
        stopMonitorTimer()
        TimerDataStore.getInstance(mContext!!).clearAll()
        manualCheckInOffAndAllowUserOn()
        setTimerType(TimerType.TimerTypeNone)
        setTimerCount(TimerCount.TimerNone)

    }

    private fun setTimerTypeValue(timerType: String) {
        when(timerType){
            mContext?.getText(R.string.never)?.toString() -> setTimerType(TimerType.TimerTypeNever)
            mContext?.getText(R.string.until_turned_off)?.toString() -> setTimerType(TimerType.TimerTypeUntilTurnedOff)
            mContext?.getText(R.string.until_a_specific_item)?.toString() -> setTimerType(TimerType.TimerTypeUntilSpecificTime)
            mContext?.getText(R.string.once)?.toString() -> setTimerType(TimerType.TimerTypeOnce)
            mContext?.getText(R.string.twice)?.toString() -> setTimerType(TimerType.TimerTypeTwice)
        }
    }



    private fun isTimerEnable(): Boolean {
        var isTimerEnable = true
       if (TimerConfiguration.getInstance().isTimerEnable(mContext!!)) {
            if (mTimerCount == 1 && getTimerType() === TimerType.TimerTypeOnce) {
                isTimerEnable = false
            } else if (mTimerCount == 0 && getTimerType() === TimerType.TimerTypeNever) {
                isTimerEnable = false
            } else if (mTimerCount == 2 && getTimerType() === TimerType.TimerTypeTwice) {
                isTimerEnable = false
            } else if(getTimerCount() == TimerCount.TimerSpecficTime) {
                isTimerEnable = false
            }
        } else {
           isTimerEnable =  true
        }
        return isTimerEnable
    }
    private fun clearTimer() {
        setTimerCount(TimerCount.TimerNone)
        setTimerType(TimerType.TimerTypeNone)
    }

    interface OnTimerListener {
        fun onTimerComplete()
    }
}