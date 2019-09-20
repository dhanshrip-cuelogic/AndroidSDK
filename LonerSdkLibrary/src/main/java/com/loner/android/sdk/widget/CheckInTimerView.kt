package com.loner.android.sdk.widget


import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
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
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.data.sdkconfiguraton.AppConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerConfiguration
import com.loner.android.sdk.data.timerconfiguration.TimerDataStore
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.model.VibrationManager
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver
import com.loner.android.sdk.receiver.GPSSettingReceiver
import com.loner.android.sdk.utils.*
import com.loner.android.sdk.webservice.network.networking.NetworkStatus


class CheckInTimerView : BaseView, ManualCheckInListener, TimerListener, CheckViewUpdateListener, CheckInTimerPresenter.View,
        ConnectionBroadcastReceiver.NetworkConnectionStatusListener, GPSSettingReceiver.GPSSettingListener {

    private var mCheckInTimerDisable: TextView? = null
    private var mCheckInTimerDescription: TextView? = null
    private var mCheckInTimer: TextView? = null
    private var mSetTimerButton: Button? = null
    private var mCheckInButton: Button? = null
    private var mContext: Context? = null
    private var mCurrentActivity: Context? = null
    private var mTimerCount = 0
    private var isTimerEnable = false
    private var mTimerListener: OnTimerListener? = null
    private var isCountdownTimer = false

    /*Timer implementation*/

    enum class TimerCount {
        TimerSpecificTime,
        TimerNone
    }

    enum class TimerType {
        TimerTypeNever,
        TimerTypeUntilTurnedOff,
        TimerTypeOnce,
        TimerTypeTwice,
        TimerTypeUntilSpecificTime,
        TimerTypeNone
    }

    private var timerType = TimerType.TimerTypeNone
    private var timerSpecificType = TimerCount.TimerNone
    private var manualCheckInTime: Int = 0
    private var isCheckInAlert: Boolean = false
    private var checkInTimerPresenter: CheckInTimerPresenter? = null


    companion object {
        private var instance: CheckInTimerView? = null

        fun setCheckInTimerView(instance: CheckInTimerView?) {
            this.instance = instance
        }

        fun getCheckInTimerView(): CheckInTimerView? {
            return instance ?: null
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
        val checkInTimerView = inflate(context, R.layout.check_in_view, null)
        val viewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 185f, resources.displayMetrics)
        val params = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, viewHeight.toInt())
        registerReceiver(mContext!!)
        gpsSettingReceiver.setGPSSettingListener(this)
        mNetworkConnectionStatusReceivers.setNetworkConnectionListener(this)
        checkInTimerView.layoutParams = params
        retryOverlay?.layoutParams = params
        baseLayout?.addView(checkInTimerView)
        setCheckInTimerView(this)
        checkInTimerPresenter = CheckInTimerPresenter(this)
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


    @JvmOverloads
    fun loadCheckInTimerComponent(context: Context, isAllowUserToConfigure: Boolean?, isManualCheckInEnabled: Boolean?, timerValue: Int?, timerListener: OnTimerListener? = null) {
        mCurrentActivity = context
        manualCheckInTime = timerValue!!
        timerListener?.let { mTimerListener = timerListener }
        if (isAllowUserToConfigure == null || isManualCheckInEnabled == null || timerValue == null) {
            throw IllegalStateException("All value should not be null")
        }
        checkInTimerPresenter?.monitoringStart(isAllowUserToConfigure,isManualCheckInEnabled,timerValue)
    }

    override fun monitorStart(checkTimeSecond: Long) {
        AppConfiguration.getInstance().setTimeBetweenCheckinLng(mContext!!, checkTimeSecond)
        AppConfiguration.getInstance().setManualCheckIn(mContext!!, true)
        AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!, true)
    }

    fun pausedMonitorTimer() {
        checkInTimerPresenter?.pausedMonitorTimer()
    }
    private fun getTimerType(): TimerType {
        return timerType
    }

    private fun setTimerType(timerType: TimerType) {
        this.timerType = timerType
    }

    private fun getTimerCount(): TimerCount {
        return timerSpecificType
    }

    private fun setTimerCount(timerCount: TimerCount) {
        this.timerSpecificType = timerCount
    }

    override fun manualCheckInOnAndAllowUserOff() {
        mCheckInTimerDisable?.visibility = View.GONE
        mCheckInTimerDescription?.visibility = View.VISIBLE
        mCheckInTimerDescription?.setText(R.string.countdown_required_to_check_in)
        mCheckInTimer?.visibility = View.VISIBLE
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
    }

    override fun manualCheckInAndAllowUserOn() {
        mCheckInTimerDescription?.visibility = View.VISIBLE
        mCheckInTimerDescription?.setText(R.string.countdown_required_to_check_in)
        mCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.VISIBLE
        mCheckInTimerDisable?.visibility = View.GONE
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!, R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!, R.color.black))
        mSetTimerButton?.setText(R.string.change_timer)
        mCheckInTimer?.visibility = View.VISIBLE
    }

    override fun manualCheckInAndAllowUserOff() {
        mCheckInTimerDescription?.visibility = View.GONE
        mCheckInTimerDescription?.gravity = Gravity.CENTER_HORIZONTAL
        mCheckInTimerDescription?.setText(R.string.change_timer_disable_description)
        mCheckInTimerDisable?.visibility = View.VISIBLE
        mCheckInButton?.visibility = View.VISIBLE
        mSetTimerButton?.visibility = View.GONE
        mCheckInButton?.setText(R.string.send_message)
        mCheckInTimer?.visibility = View.GONE
    }

    override fun manualCheckInOffAndAllowUserOn() {
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
        mSetTimerButton?.background = ContextCompat.getDrawable(mContext!!, R.drawable.btn_change_timer_selector)
        mSetTimerButton?.setTextColor(ContextCompat.getColor(mContext!!, R.color.black))
        mCheckInButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mSetTimerButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.check_in_button_size))
        mCheckInTimer?.visibility = View.GONE
    }
    override fun onMonitorTimerFinish() {
        isCheckInAlert = true
        if (NetworkStatus().isNetworkAvailable(mContext!!)) {
            requestForCheckIn()
        }
    }

    override fun onCheckTimerViewUpdate(isAck: Boolean) {
        isCheckInAlert = false
        if (isAck) acknowledgeForCheckIn()
        if (AppConfiguration.getInstance().isTimerManualCheckInEnable(mContext!!) && !TimerConfiguration.getInstance().isTimerEnable(mContext!!) && getTimerCount() === TimerCount.TimerNone && !isTimerEnable) {
            checkInTimerPresenter?.startMonitorTimer()
        } else if (!isTimerEnable()) {
            clearTimer()
            AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!, false)
            TimerDataStore.getInstance(mContext!!).clearAll()
            manualCheckInOffAndAllowUserOn()
            checkInTimerPresenter?.stopMonitorTimer()
        } else if (TimerConfiguration.getInstance().isTimerEnable(mContext!!)) {
            manualCheckInAndAllowUserOn()
            checkInTimerPresenter?.startMonitorTimer()
        }
    }

    private fun acknowledgeForCheckIn() {
        Loner.getClient().sendMessage(mContext!!, "Manual Check-in completed", null)
    }

    override fun onMissCheckInAlert() {
        isCheckInAlert = false
        sendMissedCheckInAlert()
        val intent = Intent(mContext, MissedCheckInActivity::class.java)
        mContext?.startActivity(intent)
    }

    override fun timerStateChange(isState: Boolean) {
        isCountdownTimer = isState
    }

    private fun sendMissedCheckInAlert() {
        Loner.getClient().sendAlertApi(mContext!!, "missed_check_in", null)
    }

    private fun requestForCheckIn() {
        DocleanUp.doCleanAllActivity()
        mCheckInTimer!!.text = "00:00:00"
        mTimerCount++
        mTimerListener?.onTimerComplete()
                ?: LonerDialog.getInstance().showCheckInAlert(mCurrentActivity!!, mContext?.getText(R.string.check_in_required).toString(),
                        mContext?.getText(R.string.press_ok_to_check_in_now).toString(), null)
        Loner.getClient().sendNotification(mContext!!, "manual_check_in_pending", null)
        LocationUpdate.getInstance(mContext!!).getLastLocation()
    }

    override fun onViewUpdateOnTimer(millisUntilFinished: Long) {
        if (0 >= TimerValidation.getMinuteDifferenceTime(TimerConfiguration.getInstance().getSpecificTimeCheckIn(mContext!!)) && getTimerType() === TimerType.TimerTypeUntilSpecificTime) {
            setTimerCount(TimerCount.TimerSpecificTime)
            checkInTimerPresenter?.stopMonitorTimer()
            Loner.getClient().showCheckInAlertDialog(mContext!!, mContext?.getText(R.string.check_in_now_to_turn_off).toString(),
                    mContext?.getText(R.string.check_in_now_to_turn_off_dec).toString(), null)
        } else {
            Log.d("TAG", "Time remaining " + convertCheckInTextFormat(millisUntilFinished))
            checkInTimerPresenter?.setCurrentTimeInMilisecond(millisUntilFinished)
            mCheckInTimer!!.text = convertCheckInTextFormat(millisUntilFinished)
        }
    }

    override fun manualCheckInCompleted(timer: Boolean) {
        if (timer && isCountdownTimer) {
            mTimerCount++
            onCheckTimerViewUpdate(false)
        }

    }

    override fun alertCheckInCompleted(timer: Boolean) {
        if (!isCountdownTimer) {
            return
        } else if (timer) {
            checkInTimerPresenter?.stopMonitorTimer()
            checkInTimerPresenter?.startMonitorTimer()
        } else {
            checkInTimerPresenter?.startMonitorTimer()
        }

    }

    override fun setNewTimer() {
        isTimerEnable = true
        mTimerCount = -1
        setTimerTypeValue(TimerConfiguration.getInstance().getRepeatTimerType(mContext!!))
        manualCheckInAndAllowUserOn()
        checkInTimerPresenter?.setNewTimer(AppConfiguration.getInstance().getTimeBetweenCheckInLng(mContext!!).toInt())
        checkInTimerPresenter?.startMonitorTimer()
    }

    override fun disableTimer() {
        AppConfiguration.getInstance().setTimerManualCheckInEnable(mContext!!, false)
        checkInTimerPresenter?.stopMonitorTimer()
        TimerDataStore.getInstance(mContext!!).clearAll()
        manualCheckInOffAndAllowUserOn()
        setTimerType(TimerType.TimerTypeNone)
        setTimerCount(TimerCount.TimerNone)

    }

    private fun setTimerTypeValue(timerType: String) {
        when (timerType) {
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
            } else if (getTimerCount() == TimerCount.TimerSpecificTime) {
                isTimerEnable = false
            }
        } else {
            isTimerEnable = true
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
        txtRetryOverlay?.text = "Waiting to reconnect"
        mSetTimerButton?.isEnabled = false
        mCheckInButton?.isEnabled = false
        retryOverlay?.alpha = 0.8f
        retryOverlay?.visibility = View.VISIBLE

        if (isCheckInAlert) {
            LonerDialog.getInstance().dismissCheckInAlertDialog()
            SoundManager.getInstance(mContext!!).stopSound()
            VibrationManager.getInstance(mContext!!).stopVibration()
        }
    }

    override fun onNetworkConnected() {
        mSetTimerButton?.isEnabled = true
        mCheckInButton?.isEnabled = true
        retryOverlay?.visibility = View.GONE
        if (isCheckInAlert) requestForCheckIn()
    }

    override fun onGPSDisabled(context: Context) {
        txtRetryOverlay?.text = "Location service is disabled"
        mSetTimerButton?.isEnabled = false
        mCheckInButton?.isEnabled = false
        retryOverlay?.visibility = View.VISIBLE
        checkInTimerPresenter?.stopMonitorTimer()
        clearTimer()
    }

    override fun onGPSEnabled(context: Context) {
        mSetTimerButton?.isEnabled = true
        mCheckInButton?.isEnabled = true
        retryOverlay?.visibility = View.GONE
        loadCheckInTimerComponent(context, true, true, manualCheckInTime)
    }

}