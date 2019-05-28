package com.loner.android.sdk.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.utils.SliderUnlockWidget
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface

/**
 * This call provide a callback of Emergency Slider swipe left to right.
 */
class EmergencySlider : RelativeLayout, SliderUnlockWidget.SliderUnlockWidgetListener {

    private var redComponentView: View? = null
    private var emergencySlider: SliderUnlockWidget? = null
    private var listener: EmergencySliderListetener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.emergency_slider_view, this)
        redComponentView = findViewById(R.id.redComponentView)
        emergencySlider = findViewById(R.id.seekEmergency)
        emergencySlider!!.setSliderUnlockListener(this)
        emergencySlider!!.setRedComponentView(redComponentView!!)
        emergencySlider!!.progress = 0

    }

    override fun onSliderUnlock(sliderUnlockWidget: SliderUnlockWidget) {
        emergencySlider!!.progress = 0
        emergencySlider!!.updateTheRedComponent(0)
        listener!!.onEmergencySlide()

    }

    fun setOnEmergencySliderListetener(listener: EmergencySliderListetener) {
        this.listener = listener
    }

    override fun onSliderTouched() {

    }

   interface EmergencySliderListetener {
       fun  onEmergencySlide()
   }
}
