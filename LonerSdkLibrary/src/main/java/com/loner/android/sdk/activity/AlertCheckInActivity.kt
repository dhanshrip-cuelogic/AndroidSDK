package com.loner.android.sdk.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.location.LocationUpdate
import com.loner.android.sdk.viewModel.AlertActivityViewModel
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_manual_check_in.*

class AlertCheckInActivity : BaseActivity() {
    private var manualCheckInListener: ManualCheckInListener? = null
    private lateinit var mQuickNoteSpinner: Spinner
    private lateinit var mCustomNoteEditText: EditText
    var progressBarDialog: ProgressDialog? = null
    private var mLastClickTime: Long = 0
    private lateinit var activityViewModel: AlertActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_check_in)
        activityViewModel = ViewModelProviders.of(this).get(AlertActivityViewModel::class.java)
        setAlertCheckInActivity(this)
        manualCheckInListener = CheckInTimerView.getCheckInTimerView()
        initUI()
        observeViewModel()
        btn_send_check_in.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            activityViewModel.sendAlertCheckIn(mQuickNoteSpinner.selectedItemPosition, mCustomNoteEditText.text.toString().trim(), mQuickNoteSpinner.selectedItem.toString())
        }
        btn_cancel_check_in.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            manualCheckInListener?.alertCheckInCompleted(false)
            finish()
        }
    }

    private fun observeViewModel() {
        activityViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (isLoading) progressBarDialog?.show() else progressBarDialog?.dismiss()
            }
        })

        activityViewModel.responseDataSuccess.observe(this, Observer { isResponse ->
            isResponse?.let {
                if (isResponse) {
                    manualCheckInListener?.alertCheckInCompleted(true)
                    finish()
                }
            }
        })
        activityViewModel.apiErrorLoadErrorMsg.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })

    }

    private fun initUI() {
        progressBarDialog = ProgressDialog(this)
        progressBarDialog!!.setMessage("Sending check-in note..")
        progressBarDialog!!.setCancelable(false)
        mQuickNoteSpinner = findViewById(R.id.spinQuickNoteFull)
        mCustomNoteEditText = findViewById(R.id.customTextEdt)
        mCustomNoteEditText.clearFocus()
    }


    companion object {

        private var INSTANCE: AlertCheckInActivity? = null

        private fun setAlertCheckInActivity(instance: AlertCheckInActivity?) {
            INSTANCE = instance
        }

        fun getAlertCheckInActivity(): AlertCheckInActivity? {
            return INSTANCE ?: null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        setAlertCheckInActivity(null)
    }

    override fun onNetworkConnected() {
        super.onNetworkConnected()
        btn_send_check_in.isEnabled = true
    }

    override fun onNetworkDisconnected() {
        super.onNetworkDisconnected()
        btn_send_check_in.isEnabled = false
    }

}