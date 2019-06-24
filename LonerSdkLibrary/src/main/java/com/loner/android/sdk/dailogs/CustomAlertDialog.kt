package com.loner.android.sdk.dailogs


import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.loner.android.sdk.R
import com.loner.android.sdk.utils.Constant

class CustomAlertDialog constructor(context: Context, title: CharSequence?, subject: CharSequence?, buttontext: CharSequence?, lonerDialogListener: LonerDialogListener?): Dialog(context, R.style.dialogTheme) {

    private lateinit var txtCustomDialogTitle: TextView
    private lateinit var txtCustomDialogSubject:TextView
    private var titleText: CharSequence? = null
    private var subjectText: CharSequence? = null
    private var btnText: CharSequence? = null
    private lateinit var btnCustomDialogOk: Button
    private var mlonerDialogListener:LonerDialogListener? = null


    init {
        title?. let { this.titleText=title }
        subject?.let { this.subjectText = subject }
        buttontext?.let { this.btnText = buttontext }
        lonerDialogListener?.let { this.mlonerDialogListener = lonerDialogListener }
    }


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
         setContentView(R.layout.alert_dialog_layout)
        setCanceledOnTouchOutside(true)
        if (window != null) {
            window!!.setDimAmount(Constant.DIM_VALUE_FOR_DIALOG)
            window!!.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparency_colour)))
        }
        initView()
        updateTextualContent()
    }

    private fun updateTextualContent() {
        if (titleText != null) {
            txtCustomDialogTitle.text = titleText
        } else {
            txtCustomDialogTitle.visibility = View.GONE
        }

        txtCustomDialogSubject.text = subjectText
        btnCustomDialogOk.text = btnText
    }

    private fun initView() {
        txtCustomDialogTitle = findViewById(R.id.customDialogTitle)
        txtCustomDialogSubject = findViewById(R.id.customDialogSubject)
        btnCustomDialogOk = findViewById(R.id.customDialogOk)
        btnCustomDialogOk.setOnClickListener {
            this.mlonerDialogListener?.onPositiveButtonClicked()
            dismiss()
        }
    }
}
