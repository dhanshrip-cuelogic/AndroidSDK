package com.loner.android.sdk.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Vibrator


class VibrationManager private constructor(context: Context) {

    private var mVibrationHandler: Handler? = null
    private var mVibrationRunnable: Runnable? = null
    private var mContext: Context = context
    private var mVibration = mContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    companion object {
        @Volatile
        private var INSTANCE: VibrationManager? = null

        fun getInstance(context: Context): VibrationManager {
            return INSTANCE ?: synchronized(this) {
                VibrationManager(context).also {
                    INSTANCE = it

                }
            }
        }
    }

    private fun checkVibrationPermission(): Boolean {
        val pm = this.mContext.packageManager
        return pm.checkPermission(Manifest.permission.VIBRATE, mContext.packageName) == PackageManager.PERMISSION_GRANTED
    }


    fun startVibrationForAlert() {
        try {
            if (checkVibrationPermission() && mVibration.hasVibrator()) {
                val durationInMilli: Long = 500
                mVibrationHandler = Handler()
                mVibrationRunnable = Runnable {
                    try {
                        mVibration.vibrate(durationInMilli)
                        mVibrationHandler?.postDelayed(mVibrationRunnable, 1000)
                    } catch (e: Exception) {
                    }
                }
                mVibrationHandler?.postDelayed(mVibrationRunnable, 1000)
            }
        } finally {

        }

    }

    fun stopVibration() {
        try {
            if (mVibration != null && mVibration.hasVibrator()) {
                mVibration.cancel()
            }
            if (mVibrationHandler != null && mVibrationRunnable != null) {
                mVibrationHandler?.removeCallbacks(mVibrationRunnable)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun vibrationForCheckIn(){
        try {
            if (mVibration != null && mVibration.hasVibrator()) {
                mVibration.cancel()
                mVibration.vibrate(500)

            }
            if (mVibrationHandler != null && mVibrationRunnable != null) {
                mVibrationHandler?.removeCallbacks(mVibrationRunnable)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}