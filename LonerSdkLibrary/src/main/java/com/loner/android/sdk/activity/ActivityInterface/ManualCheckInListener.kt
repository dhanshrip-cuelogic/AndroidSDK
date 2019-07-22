package com.loner.android.sdk.activity.ActivityInterface



interface ManualCheckInListener {
    fun manualCheckInCompleted(timer:Boolean)
    fun alertCheckInCompleted(timer:Boolean)

}