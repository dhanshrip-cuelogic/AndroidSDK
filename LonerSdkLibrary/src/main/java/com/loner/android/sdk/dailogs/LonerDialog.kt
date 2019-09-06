package com.loner.android.sdk.dailogs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.loner.android.sdk.R
import com.loner.android.sdk.countdowntimer.MissCheckInCountDownTimer
import com.loner.android.sdk.countdowntimer.MissCheckInTimerListener
import com.loner.android.sdk.model.VibrationManager
import com.loner.android.sdk.utils.Constant
import com.loner.android.sdk.utils.SoundManager
import com.loner.android.sdk.widget.CheckInTimerView


class LonerDialog private constructor() {
  private var customAlertDialog: CustomAlertDialog? = null
    private  lateinit var checkInAlertDialog:CustomAlertDialog
  private var missCheckInCountDownTimer:MissCheckInCountDownTimer? = null
    companion object {
        @Volatile
        private var lonerDialogInstance: LonerDialog? = null

        fun getInstance(): LonerDialog {
            return lonerDialogInstance ?: synchronized(this) {
                LonerDialog().also {
                    lonerDialogInstance = it
                }
            }
        }
    }


    fun showAlertDialog(context: Context, title: String?, message: String?, buttonText: String?,lonerDialogListener: LonerDialogListener?) {
        var buttonText = buttonText
        if (buttonText == null)
            buttonText = "OK"

        customAlertDialog = CustomAlertDialog(context, title, message, buttonText,lonerDialogListener)
        if (customAlertDialog?.window != null) {
            customAlertDialog?.window!!.setDimAmount(Constant.DIM_VALUE_FOR_DIALOG)
            customAlertDialog!!.window.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparency_colour)))
            customAlertDialog!!.show()
        }

    }

    fun showCheckInAlert(context: Context, title: String?, message: String?, buttonText: String?){

         missCheckInCountDownTimer = MissCheckInCountDownTimer(30*1000,1000, object:MissCheckInTimerListener{
            override fun onCountDownFinish() {
                CheckInTimerView.getCheckInTimerView()?.onMissCheckInAlert()
                checkInAlertDialog.dismiss()
                SoundManager.getInstance(context).stopSound()
            }

        } )
        missCheckInCountDownTimer?.start()

        var buttonText = buttonText
        if (buttonText == null)
            buttonText = "OK"
        checkInAlertDialog = CustomAlertDialog(context, title, message, buttonText,
                object : LonerDialogListener {
                    override fun onPositiveButtonClicked() {
                     CheckInTimerView.getCheckInTimerView()?.onCheckTimerViewUpdate()
                     missCheckInCountDownTimer?.cancel()
                        SoundManager.getInstance(context).stopSound()
                    }

                })
        if (checkInAlertDialog.window != null) {
            checkInAlertDialog.window.setDimAmount(Constant.DIM_VALUE_FOR_DIALOG)
            checkInAlertDialog.window.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparency_colour)))
            checkInAlertDialog.show()
            SoundManager.getInstance(context).playSoundForCheckIn()
            VibrationManager.getInstance(context).vibrationForCheckIn()
        }

    }
     fun dismissCheckInAlertDialog(){
         missCheckInCountDownTimer?.cancel()
         missCheckInCountDownTimer = null
         if(checkInAlertDialog!=null && checkInAlertDialog.isShowing) checkInAlertDialog.dismiss()
     }

    fun isShowingAlert(): Boolean {
       return customAlertDialog?.isShowing?:false
    }
}