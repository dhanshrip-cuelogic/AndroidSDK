package com.loner.android.sdk.model.respons

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.loner.android.sdk.activity.ActivityInterface.PermissionResultCallback
import com.loner.android.sdk.activity.PermissionActivity
import java.lang.ref.WeakReference

class LonerPermission private constructor(context: Context) {
    private var context: WeakReference<Context> = WeakReference(context)

    companion object {
        @Volatile
        private var sharedInstance: LonerPermission? = null
        private var permissionResultCallback: PermissionResultCallback? = null

        fun getInstance(context: Context,permissionResultCallback: PermissionResultCallback): LonerPermission {
            return sharedInstance ?: synchronized(this) {
                LonerPermission(context).also {
                    sharedInstance = it
                    this.permissionResultCallback = permissionResultCallback
                }
            }
        }

        fun onPermissionGranted(){
            permissionResultCallback?.let {
                it.onPermissionGranted()
            }
        }
    }

    fun checkPermissions(){
        Log.d("TAG","verifying call from client")
        startPermissionActivity()
    }

    private fun startPermissionActivity() {
        val context = this.context.get() ?: return
        val intent = Intent(context, PermissionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        if (context is Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

}