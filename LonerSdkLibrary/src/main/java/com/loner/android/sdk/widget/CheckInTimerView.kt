package com.loner.android.sdk.widget

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.loner.android.sdk.R

import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.activity.ActivityInterface.TimerListener
import com.loner.android.sdk.activity.CheckInActivity
import com.loner.android.sdk.activity.MissedCheckInActivity
import com.loner.android.sdk.activity.SetTimerActivity
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.countdowntimer.CheckInTimerListener
import com.loner.android.sdk.countdowntimer.MonitoringCountDownTimer
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.dailogs.LonerDialogListener
import com.loner.android.sdk.data.sdkconfiguraton.AppConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerDataStore
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.model.VibrationManager
import com.loner.android.sdk.model.respons.LonerPermission
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver
import com.loner.android.sdk.receiver.GPSSettingReceiver
import com.loner.android.sdk.utils.*
import com.loner.android.sdk.webservice.network.networking.NetworkStatus




class CheckInTimerView: BaseView, CheckInTimerListener,ManualCheckInListener,TimerListener,CheckViewUpdateListener{
    private var mCheckInTimerDisable: TextView? = null
    private var mCheckInTimerDescription: TextView? = null
    private var mCheckInTimer: TextView? = null
    private var mSetTimerButton: Button? = null
    private var mCheckInButton: Button? = null
    private var mContext: Context? = null
    private var mCurrentActivity: Context? = null
    private var mTimerCount = 0
    private var isTimerEnable = false
    private var mTimerListener:OnTimerListener? = null
    /*Timer implementation*/
    enum class TimerState {
        Stopped, Paused, Running
    }
    enum class TimerCount {
        TimerSpecificTime,
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
    private var manualCheckInTime: Int = 0
    private var monitoringTimer: MonitoringCountDownTimer? = null
    private var isCheckInAlert:Boolean = false

    companion object {
        private var instance : CheckInTimerView? = null

        fun setCheckInTimerView(instance:CheckInTimerView?){
            this.instance = instance
        }

        fun getCheckInTimerView():CheckInTimerView? {
            return instance?:null
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
        val checkInTimerView = inflate(context,R.layout.check_in_view, null)
        val viewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 185f, resources.displayMetrics)
        val params = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, viewHeight.toInt())
        checkInTimerView.layoutParams = params
        retryOverlay?.layoutParams = params
        baseLayout?.addView(checkInTimerView)

        setCheckInTimerView(this)
        LocationUpdate.getInstance(mContext!!).getLastLocation()
        mCheckInTimerDisable = findViewById(R.id.txtManualDisabled)
        mCheckInTimerDescription = findViewById(R.id.txtCheckin)
        mCheckInTimer = findViewById(R.id.txtTimer)
        mSetTimerButton = findViewById(R.id.btnSetTimerButton)
        mCheckInButton = findViewById(R.id.btnCheckinWithNotes)
        TimerDataStore.getInstance(mContext!!).clearAll()
        mSetTimerButton!!.setOnClickListener {
            val intent = Intent(mContext, SetTimerActivity::class.java)
            mContext?.startActivity(intent)
        }
        mCheckInButton!!.setOnClickListener {
            val intent = Intent(mContext, CheckInActivity::class.java)
            mContext?.startActivity(intent)
        }
    }

    @JvmOverloads fun loadCheckInTimerComponent(context:Context,isAllowUserToConfigure : Boolean?,isManualCheckInEnabled:Boolean?, timerValue : Int?, timerListener: OnTimerListener? = null ){
        // TODO:: Remove permission check and assinging permission callback from here,
        //  once it has beed asked inside loner client class
        val permissionResultCallback:PermissionResultCallback = context as PermissionResultCallback
        Loner.getClient().checkPermission(context,object : PermissionResultCallback{
            override fun onPermissionGranted() {
                permissionResultCallback.onPermissionGranted()
                mCurrentActivity = context
                manualCheckInTime = timerValue!!
                timerListener?.let { mTimerListener = timerListener }
                if(isAllowUserToConfigure == null || isManualCheckInEnabled == null || timerValue == null){
                    throw IllegalStateException("All value should not be null")
                }
                checkVisibilityOfView(isAllowUserToConfigure, isManualCheckInEnabled)
                if(timerValue != 0 && isManualCheckInEnabled){
                    val checkTimeInSecond = convertMinuteToSecond(timerValue)
                    AppConfiguration.getInstance().setTimeBetweenCheckinLng(mContext!!,checkTimeInSecond)
                    AppConfiguration.getInstance().setManualCheckIn(mContext!!,true)
                    AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!,true)
                    monitorTimerLengthMillisecond = convertSecondToMillisecond(checkTimeInSecond)
                    startMonitorTimer()
                }
            }
        })

    }

    private fun startMonitorTimer(){
        if(getTimerStatus() == TimerState.Running) stopMonitorTimer()
        val timerValue = if (getTimerStatus() == TimerState.Paused)
            currentTimerLengthMillisecond
        else
            monitorTimerLengthMillisecond
        monitoringTimer = MonitoringCountDownTimer(timerValue,1000,this)
        monitoringTimer!!.start()
        setTimerStatus(TimerState.Running)
    }

     fun pausedMonitorTimer() {
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
        mCheckInTimerDescription?.visibility = View.VISIBLE
        mCheckInTimerDescription?.setText(R.string.countdown_required_to_check_in)
        mCheckInTimer?.visibility = View.VISIBLE
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
    }

    private fun manualCheckInAndAllowUserOn(){
        mCheckInTimerDescription?.visibility = View.VISIBLE
        mCheckInTimerDescription?.setText(R.string.countdown_required_to_check_in)
        mCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mCheckInTimerDisable?.visibility = View.GONE
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!,R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!,R.color.black))
        mSetTimerButton?.setText(R.string.change_timer)
        mCheckInTimer?.visibility = View.VISIBLE
    }

    private fun manualCheckInAndAllowUserOff(){
        mCheckInTimerDescription?.visibility = View.GONE
        mCheckInTimerDescription?.gravity = Gravity.CENTER_HORIZONTAL
        mCheckInTimerDescription?.setText(R.string.change_timer_disable_description)
        mCheckInTimerDisable?.visibility = View.VISIBLE
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
        mCheckInButton?.setText(R.string.send_message)
        mCheckInTimer?.visibility = View.GONE
    }

    private fun manualCheckInOffAndAllowUserOn(){
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mCheckInTimerDisable?.visibility = View.VISIBLE
        mCheckInTimerDisable?.gravity = Gravity.LEFT
        mCheckInTimerDescription?.visibility = View.VISIBLE
        mCheckInTimerDescription?.setText(R.string.change_timer_disable_description)
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mSetTimerButton?.setText(R.string.set_timer)
        mCheckInButton?.setText(R.string.send_message)
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!,R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!,R.color.black))
        mCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mCheckInTimer?.visibility = View.GONE
    }

    override fun onMonitorTimerFinish() {
        isCheckInAlert = true
        if(NetworkStatus().isNetworkAvailable(mContext!!)) {
            requestForCheckIn()
        }
    }
    override fun onCheckTimerViewUpdate() {
        isCheckInAlert = false
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
        Loner.getClient().sendMessage(mContext!!,"Manual Check-in completed",null)
    }

    override fun onMissCheckInAlert() {
        isCheckInAlert = false
        sendMissedCheckInAlert()
        val intent = Intent(mContext, MissedCheckInActivity::class.java)
        mContext?.startActivity(intent)
    }

    private fun sendMissedCheckInAlert() {
        Loner.getClient().sendAlertApi(mContext!!,"missed_check_in",null)
    }

    private fun requestForCheckIn() {
        DocleanUp.doCleanAllActivity()
        mCheckInTimer!!.text = "00:00:00"
        mTimerCount++
        mTimerListener?.onTimerComplete()
                ?: LonerDialog.getInstance().showCheckInAlert(mCurrentActivity!!, mContext?.getText(R.string.check_in_required).toString(),
                        mContext?.getText(R.string.press_ok_to_check_in_now).toString(), null)
        Loner.getClient().sendNotification(mContext!!,"manual_check_in_pending",null)
        LocationUpdate.getInstance(mContext!!).getLastLocation()
    }

    override fun onViewUpdateOnTimer(millisUntilFinished: Long) {
        if (0 >= TimerValidation.getMinuteDifferenceTime(TimerConfiguration.getInstance().getSpecificTimeCheckIn(mContext!!)) && getTimerType() === TimerType.TimerTypeUntilSpecificTime) {
            setTimerCount(TimerCount.TimerSpecificTime)
            stopMonitorTimer()
            Loner.getClient().showCheckInAlertDialog(mContext!!, mContext?.getText(R.string.check_in_now_to_turn_off).toString(),
                    mContext?.getText(R.string.check_in_now_to_turn_off_dec).toString(),null)

        }else {
            Log.d("TAG","Time remaining "+convertCheckInTextFormat(millisUntilFinished))
            currentTimerLengthMillisecond = millisUntilFinished
            mCheckInTimer!!.text = convertCheckInTextFormat(millisUntilFinished)
        }
    }

    override fun manualCheckInCompleted(timer: Boolean) {
        if(timer) {
            mTimerCount++
            stopMonitorTimer()
            startMonitorTimer()
        }

    }

    override fun alertCheckInCompleted(timer: Boolean) {
        if(timer) {
            stopMonitorTimer()
            startMonitorTimer()
        }else {
            startMonitorTimer()
        }

    }

    override fun setNewTimer() {
        isTimerEnable = true
        mTimerCount = -1
        setTimerTypeValue(TimerConfiguration.getInstance().getRepeatTimerType(mContext!!))
        manualCheckInAndAllowUserOn()
        monitorTimerLengthMillisecond = convertSecondToMillisecond(AppConfiguration.getInstance().getTimeBetweenCheckInLng(mContext!!))
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
            } else if(getTimerCount() == TimerCount.TimerSpecificTime) {
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

    override fun onNetworkDisconnected() {
        super.onNetworkDisconnected()
        mSetTimerButton?.isEnabled = false
        mCheckInButton?.isEnabled = false
        if(isCheckInAlert) {
            LonerDialog.getInstance().dismissCheckInAlertDialog()
            SoundManager.getInstance(mContext!!).stopSound()
            VibrationManager.getInstance(mContext!!).stopVibration()
        }
    }

    override fun onNetworkConnected() {
        super.onNetworkConnected()
        mSetTimerButton?.isEnabled = true
        mCheckInButton?.isEnabled = true
        if(isCheckInAlert) requestForCheckIn()
    }

    override fun onGPSDisabled(context: Context) {
        super.onGPSDisabled(context)
        mSetTimerButton?.isEnabled = false
        mCheckInButton?.isEnabled = false
        retryOverlay?.visibility = View.VISIBLE
        stopMonitorTimer()
        clearTimer()
    }

    override fun onGPSEnabled(context: Context) {
        super.onGPSEnabled(context)
        mSetTimerButton?.isEnabled = true
        mCheckInButton?.isEnabled = true
        retryOverlay?.visibility = View.GONE
        loadCheckInTimerComponent(context, true, true, manualCheckInTime)
    }

}