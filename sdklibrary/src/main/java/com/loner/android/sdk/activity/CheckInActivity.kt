package com.loner.android.sdk.activity

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.loner.android.sdk.R
import com.loner.android.sdk.activity.ActivityInterface.ManualCheckInListener
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_manual_check_in.*

class CheckInActivity : AppCompatActivity() {
    var progressBarDailog: ProgressDialog? = null
    private lateinit var manualCheckInListener: ManualCheckInListener
    private lateinit var mQuickNoteSpinner: Spinner
    private lateinit var mCustomNoteEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_manual_check_in)
        manualCheckInListener = CheckInTimerView.getCheckInTimerView()
        initUI()

        btnSendCheckin.setOnClickListener {
            sendCheckInNoteToServer()
        }
        btnCancelCheckin.setOnClickListener {
            manualCheckInListener.manualCheckInCompeleted(false)
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
            var checkInNote =mQuickNoteSpinner.selectedItem.toString() + "\n" + customText
            sendNoteToThePortal(checkInNote)
        } else {
            sendNoteToThePortal(mQuickNoteSpinner.selectedItem.toString())
        }

    }

    private fun sendNoteToThePortal(checkInNote:String){
        progressBarDailog!!.show()
        Loner.client.sendMessage(this,checkInNote, object :ActivityCallBackInterface{
            override fun onResponseDataSuccess(successResponse: String) {
                progressBarDailog!!.dismiss()
                manualCheckInListener.manualCheckInCompeleted(true)
                finish()
            }

            override fun onResponseDataFailure(failureResponse: String) {
                Toast.makeText(applicationContext,failureResponse,Toast.LENGTH_LONG).show()
            }
        })

    }


    private fun initUI() {
        progressBarDailog = ProgressDialog(this)
        progressBarDailog!!.setMessage("Sending check-in note..")
        progressBarDailog!!.setCancelable(false)
        mQuickNoteSpinner = findViewById(R.id.spinQuickNoteFull)
        mCustomNoteEditText = findViewById(R.id.customTextEdt)
       /* val spinnerArray = resources.getStringArray(R.array.barrows_list)
        val propertyAdapter = ArrayAdapter<String>(
                this@CheckInActivity, R.layout.spinner_item_layout, R.id.title, spinnerArray)
        mQuickNoteSpinner.adapter = propertyAdapter*/
        mCustomNoteEditText.clearFocus()


    }




}
