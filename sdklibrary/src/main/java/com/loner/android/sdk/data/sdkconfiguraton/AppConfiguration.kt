package com.loner.android.sdk.data.sdkconfiguraton

import android.content.Context
import com.loner.android.sdk.model.respons.Fdu
import com.loner.android.sdk.model.respons.Mobile


class AppConfiguration private constructor() {
    companion object {
        @Volatile
        private var appConfigurationInstance: AppConfiguration? = null

        fun getInstance(): AppConfiguration {
            return appConfigurationInstance
                    ?: synchronized(this) {
                        AppConfiguration().also {
                            appConfigurationInstance = it
                        }
                    }
        }
    }

    fun setFduConfiguration(context: Context, fdu: Fdu?) {
        setManualCheckIn(context, fdu?.isManualCheckInEnabled)
        setCheckInTimeOut(context, fdu?.manualCheckInTimeOut)
    }

    fun setMobileConfiguration(context: Context, mobile: Mobile?) {
        setAllowUserToConfiguration(context, mobile?.isAllowUserToConfigure)
    }

     fun setManualCheckIn(context: Context, isMunualCheckIn: Boolean?) {
        DataStore.saveBooleanData(context, DataStore.IS_MANUAL_CHECK_IN_ON, isMunualCheckIn!!)
     }
    fun isTimerManualCheckInEnable(context: Context): Boolean {
        return DataStore.getBooleanData(context, DataStore.MANUAL_CHECK_IN, false)

    }
    fun setTimerManualCheckInEnable(context: Context,isEnable: Boolean) {
        DataStore.saveBooleanData(context, DataStore.MANUAL_CHECK_IN, isEnable)
    }
     fun setCheckInTimeOut(context: Context, checkInTimeOut: Int?) {
        DataStore.saveLongData(context, DataStore.CHECKIN_TIMER, checkInTimeOut!!.toLong())
    }

    private fun setAllowUserToConfiguration(context: Context, isAllowUserToConfiguration: Boolean?) {
        DataStore.saveBooleanData(context, DataStore.ALLOW_USER_TO_CONFIGURE, isAllowUserToConfiguration!!)
    }

    fun setTimeBetweenCheckinsLng(context: Context, timeBetweenCheckinsLng: Long) {
        DataStore.saveLongData(context, DataStore.CHECKIN_TIMER, timeBetweenCheckinsLng)
    }

    fun getTimeBetweenCheckInLng(context: Context): Long {
        return DataStore.getLongData(context, DataStore.CHECKIN_TIMER, 0)
    }
}