package com.loner.android.sdk.activity

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.loner.android.sdk.R

class EmergencyAlert : LinearLayout {
    private var onEmergencyClickListioner: OnEmergencyClickListioner? = null

    constructor(context: Context) : super(context) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    fun init() {
        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.emergency, this)
        findViewById<View>(R.id.btnEmergencyAcknowledge).setOnClickListener { onEmergencyClickListioner!!.onClick() }
    }

    interface OnEmergencyClickListioner {
        fun onClick()
    }

    fun setOnEmergencyAlert(onEmergencyAlert: OnEmergencyClickListioner) {
        this.onEmergencyClickListioner = onEmergencyAlert
    }
}
