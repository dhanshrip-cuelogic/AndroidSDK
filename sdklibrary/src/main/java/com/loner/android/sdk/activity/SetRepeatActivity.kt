package com.loner.android.sdk.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TimePicker
import com.loner.android.sdk.R
import com.loner.android.sdk.widget.CheckInTimerView
import kotlinx.android.synthetic.main.activity_set_repeation.*
import java.util.*

class SetRepeatActivity : AppCompatActivity(), DatePicker.OnDateChangedListener {
    private lateinit var listView: ListView
    private var itemRepeatTime = intArrayOf(R.string.never, R.string.until_turned_off, R.string.once, R.string.twice, R.string.until_a_specific_item)
    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private lateinit var now: Calendar
    private lateinit var pickerLinearLayout: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_set_repeation)
        setRepeatActivityInstance(this)
        init()
        btnBackButton.setOnClickListener {
            finish()
        }

    }

    private fun init() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

    }



    companion object {
        lateinit var instance :  SetRepeatActivity
        fun setRepeatActivityInstance(instance: SetRepeatActivity){
            this.instance = instance
        }

        fun getRepeatActivityInstance(): SetRepeatActivity {
            return instance
        }
    }
}
