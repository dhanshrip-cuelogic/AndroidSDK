package com.loner.android.sdk.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.loner.android.sdk.R
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface
import com.loner.android.sdk.webservice.network.networking.NetworkStatus


class AlertActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplication = application
    val loading = MutableLiveData<Boolean>()
    val responseDataSuccess = MutableLiveData<Boolean>()
    val networkStatus = MutableLiveData<Boolean>()
    val apiErrorLoadErrorMsg = MutableLiveData<String>()

    fun sendAlertCheckIn(selectedPosition: Int, customText: String, checkInNote: String) {
        if (selectedPosition == 0 && customText.trim().isEmpty()) {
            sendAlertNoteToThePortal(mApplication.getText(R.string.msg_check_in_require).toString())
        } else if (selectedPosition == 0 && customText.trim().isNotEmpty()) {
            sendAlertNoteToThePortal(customText)
        } else if (selectedPosition != 0 && customText.trim().isNotEmpty()) {
            val checkInNote = checkInNote + "\n" + customText
            sendAlertNoteToThePortal(checkInNote)
        } else {
            sendAlertNoteToThePortal(checkInNote)
        }
    }

    private fun sendAlertNoteToThePortal(sendNote: String) {
        loading.value = true
        Loner.getClient().sendMessage(mApplication, sendNote, object : ActivityCallBackInterface {
            override fun onResponseDataSuccess(successResponse: String) {
                loading.value = false
                responseDataSuccess.value = true
            }

            override fun onResponseDataFailure(failureResponse: String) {
                loading.value = false
                responseDataSuccess.value = false
                apiErrorLoadErrorMsg.value = failureResponse
            }
        })
    }
    fun sendNotification(notificationMsg : String) {
        if (NetworkStatus().isNetworkAvailable(mApplication)) {
            sendNotificationApi(notificationMsg)
            networkStatus.value = true
        } else {
            networkStatus.value = false
        }

    }

    private fun sendNotificationApi(msgNotification:String){
        Loner.getClient().sendNotification(mApplication, msgNotification,null)
    }

}