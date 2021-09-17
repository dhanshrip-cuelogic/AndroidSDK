package com.loner.android.sdk.utils


import com.loner.android.sdk.activity.CheckInActivity
import com.loner.android.sdk.activity.SetRepeatActivity
import com.loner.android.sdk.activity.SetTimerActivity

object DocleanUp {
    fun doCleanAllActivity(){
         SetTimerActivity.getTimerActivityInstance()?.finish()
         SetRepeatActivity.getRepeatActivityInstance()?.finish()
         CheckInActivity.getCheckInActivityInstance()?.finish()
    }
}