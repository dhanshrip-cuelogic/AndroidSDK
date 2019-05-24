package com.loner.android.sdk.data

import com.loner.android.sdk.model.respons.Fdu
import com.loner.android.sdk.model.respons.Mobile


class AppConfiguration private constructor() {
    companion object {
        @Volatile
        private var appConfigurationInstance: AppConfiguration? = null

        fun getInstance(): AppConfiguration {
            return appConfigurationInstance ?: synchronized(this) {
                AppConfiguration().also {
                    appConfigurationInstance = it
                }
            }
        }
    }

    fun setFduConfiguration(fdu: Fdu?){
        setManualCheckIn(fdu!!.isManualCheckInEnabled)
        setCheckInTimeOut(fdu!!.manualCheckInTimeOut)
    }

    fun setMobileConfiguration(mobile:Mobile?){
      setAllowUserToConfiguration(mobile!!.isAllowUserToConfigure)
    }

   private fun setManualCheckIn(isMunualCheckIn: Boolean?) {
     //  DataStore.saveBooleanData(LonerApplication.getAppContext(), DataStore.IS_MANUAL_CHECK_IN_ON, isMunualCheckIn!!)

   }

   private fun setCheckInTimeOut(checkInTimeOut: Int?) {
     //  DataStore.saveLongData(LonerApplication.getAppContext(), DataStore.CHECKIN_TIMER, checkInTimeOut!!.toLong())
    }

   private fun setAllowUserToConfiguration(isAllowUserToConfiguration: Boolean?) {
      // DataStore.saveBooleanData(LonerApplication.getAppContext(), DataStore.ALLOW_USER_TO_CONFIGURE, isAllowUserToConfiguration!!)
    }
}