package com.loner.android.sdk.activity


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.loner.android.sdk.R
import kotlinx.android.synthetic.main.set_timer_view.*

class SetTimerActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TIMER_REPETION_REQUEST = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_set_timer)
        repeatLayout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
      when(v?.id){
          R.id.repeatLayout -> {
              var intent = Intent(this, SetRepeatActivity::class.java)
              startActivityForResult(intent, TIMER_REPETION_REQUEST)
              overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
          }

      }
    }

}
