package com.loner.android.sdk.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_manual_check_in.*

class CheckInActivity : BaseActivity() {
    var progressBarDialog: ProgressDialog? = null
    private  var manualCheckInListener: ManualCheckInListener? = null
    private lateinit var mQuickNoteSpinner: Spinner
    private lateinit var mCustomNoteEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_manual_check_in)
        setCheckInActivityInstance(this)
        manualCheckInListener = CheckInTimerView.getCheckInTimerView()
        initUI()

        btn_send_check_in.setOnClickListener {
            sendCheckInNoteToServer()
        }
        btn_cancel_check_in.setOnClickListener {
            manualCheckInListener?.manualCheckInCompleted(false)
            finish()
        }

    }

    private fun sendCheckInNoteToServer() {
        val selectedPosition =  mQuickNoteSpinner.selectedItemPosition
        val customText = mCustomNoteEditText.text.toString().trim()
        if (selectedPosition == 0 && customText.trim().isEmpty()) {
         sendNoteToThePortal(getText(R.string.msg_check_in_require).toString())
        } else if(selectedPosition == 0 && customText.trim().isNotEmpty()) {
         sendNoteToThePortal(customText)
        } else if(selectedPosition != 0 && customText.trim().isNotEmpty()) {
            val checkInNote =mQuickNoteSpinner.selectedItem.toString() + "\n" + customText
            sendNoteToThePortal(checkInNote)
        } else {
            sendNoteToThePortal(mQuickNoteSpinner.selectedItem.toString())
        }

    }

    private fun sendNoteToThePortal(checkInNote:String){
        progressBarDialog?.show()
        Loner.getClient().sendMessage(this,checkInNote, object :ActivityCallBackInterface{
            override fun onResponseDataSuccess(successResponse: String) {
                progressBarDialog?.dismiss()
                manualCheckInListener?.manualCheckInCompleted(true)
                finish()
            }

            override fun onResponseDataFailure(failureResponse: String) {
                progressBarDialog?.dismiss()
                Toast.makeText(applicationContext,failureResponse,Toast.LENGTH_LONG).show()
            }
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

    override fun onNetworkConnected() {
        super.onNetworkConnected()
        btn_send_check_in.isEnabled = true
    }

    override fun onNetworkDisconnected() {
        super.onNetworkDisconnected()
        btn_send_check_in.isEnabled = false
    }

    companion object {
        private  var INSTANCE:CheckInActivity? = null

        private fun setCheckInActivityInstance(instance:CheckInActivity?){
            INSTANCE = instance
        }

        fun getCheckInActivityInstance(): CheckInActivity? {
           return INSTANCE?:null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setCheckInActivityInstance(null)
    }

}
