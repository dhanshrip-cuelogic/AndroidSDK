package com.loner.android.sdk.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import com.loner.android.sdk.R
import com.loner.android.sdk.activity.EmergencyAlertActivity
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.receiver.ConnectionBroadcastReceiver
import com.loner.android.sdk.receiver.GPSSettingReceiver
import com.loner.android.sdk.utils.SliderUnlockWidget
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import kotlinx.android.synthetic.main.emergency_slider_view.view.*


/**
 * This call provide a callback of Emergency Slider swipe left to right.
 */
class EmergencySlider : BaseView, SliderUnlockWidget.SliderUnlockWidgetListener, ConnectionBroadcastReceiver.NetworkConnectionStatusListener, GPSSettingReceiver.GPSSettingListener {

    private var redComponentView: View? = null
    private var emergencySlider: SliderUnlockWidget? = null
    private var listener: EmergencySliderListener? = null
    private var mContext: Context? = null
    private var alertView: View? = null
    constructor(context: Context) : super(context) {
        mContext = context
        init()
}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init()

    }


    private fun init() {
        alertView = inflate(context,R.layout.emergency_slider_view, null)
        val viewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 73f, resources.displayMetrics)
        val params = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, viewHeight.toInt())
        alertView?.layoutParams = params
        baseLayout?.addView(alertView)
        registerReceiver(mContext!!)
        gpsSettingReceiver.setGPSSettingListener(this)
        mNetworkConnectionStatusReceivers.setNetworkConnectionListener(this)
      //  val inflater = LayoutInflater.from(context)
        //inflater.inflate(R.layout.emergency_slider_view, this)
        redComponentView = findViewById(R.id.redComponentView)
        emergencySlider = findViewById(R.id.seekEmergency)
        emergencySlider?.setSliderUnlockListener(this)
        emergencySlider?.setRedComponentView(redComponentView!!)
        emergencySlider?.progress = 0

    }

    override fun onSliderUnlock(sliderUnlockWidget: SliderUnlockWidget) {
        emergencySlider?.progress = 0
        emergencySlider?.updateTheRedComponent(0)
        listener?.onEmergencySlide()
        val intent = Intent(mContext, EmergencyAlertActivity::class.java)
        mContext?.startActivity(intent)
        Loner.getClient().sendEmergencyAlertApi(mContext!!, null)


    }

    fun setOnEmergencySliderListener(listener: EmergencySliderListener?) {
        this.listener = listener
    }

    override fun onSliderTouched() {

    }

   interface EmergencySliderListener {
       fun  onEmergencySlide()
   }

    override fun onGPSEnabled(context: Context) {
        emergencySlider?.isEnabled = true
    }

    override fun onGPSDisabled(context: Context) {
        emergencySlider?.progress = 0
        emergencySlider?.isEnabled = false
    }
    override fun onNetworkDisconnected() {
        emergencySlider?.isEnabled = false

    }

    override fun onNetworkConnected() {
        emergencySlider?.isEnabled = true

    }

}
