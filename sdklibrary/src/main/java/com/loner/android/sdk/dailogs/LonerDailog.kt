package com.loner.android.sdk.dailogs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.loner.android.sdk.R
import com.loner.android.sdk.utils.Constant


class LonerDailog private constructor() {
  private  lateinit var customAlertDialog:CustomAlertDialog
    companion object {
        @Volatile
        private var lonerDailogInstance: LonerDailog? = null

        fun getInstance(): LonerDailog {
            return lonerDailogInstance ?: synchronized(this) {
                LonerDailog().also {
                    lonerDailogInstance = it
                }
            }
        }
    }


    fun showAlertDialog(context: Context, title: String?, message: String?, buttonText: String?) {
        var buttonText = buttonText
        if (buttonText == null)
            buttonText = "OK"

        customAlertDialog = CustomAlertDialog(context, title, message, buttonText)
        if (customAlertDialog.window != null) {
            customAlertDialog.window.setDimAmount(Constant.DIM_VALUE_FOR_DIALOG)
            customAlertDialog.window.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparency_colour)))
            customAlertDialog.show()
        }

    }

}