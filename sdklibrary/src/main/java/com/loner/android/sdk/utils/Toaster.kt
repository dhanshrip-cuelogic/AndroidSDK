package com.loner.android.sdk.utils

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import java.text.MessageFormat

object Toaster {

    private fun show(activity: Activity?, resId: Int, duration: Int) {
        if (activity == null)
            return

        val context = activity.application
        activity.runOnUiThread { Toast.makeText(context, resId, duration).show() }
    }

    private fun show(activity: Activity?, message: String,
                     duration: Int) {
        if (activity == null)
            return
        if (TextUtils.isEmpty(message))
            return

        val context = activity.application
        activity.runOnUiThread { Toast.makeText(context, message, duration).show() }
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_LONG] duration
     *
     * @param activity
     * @param resId
     */
    fun showLong(activity: Activity, resId: Int) {
        show(activity, resId, LENGTH_LONG)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_SHORT] duration
     *
     * @param activity
     * @param resId
     */
    fun showShort(activity: Activity, resId: Int) {
        show(activity, resId, LENGTH_SHORT)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_LONG] duration
     *
     * @param activity
     * @param message
     */
    fun showLong(activity: Activity, message: String) {
        show(activity, message, LENGTH_LONG)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_SHORT] duration
     *
     * @param activity
     * @param message
     */
    fun showShort(activity: Activity, message: String) {
        show(activity, message, LENGTH_SHORT)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_LONG] duration
     *
     * @param activity
     * @param message
     * @param args
     */
    fun showLong(activity: Activity, message: String,
                 vararg args: Any) {
        val formatted = MessageFormat.format(message, *args)
        show(activity, formatted, LENGTH_LONG)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_SHORT] duration
     *
     * @param activity
     * @param message
     * @param args
     */
    fun showShort(activity: Activity, message: String,
                  vararg args: Any) {
        val formatted = MessageFormat.format(message, *args)
        show(activity, formatted, LENGTH_SHORT)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_LONG] duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    fun showLong(activity: Activity?, resId: Int,
                 vararg args: Any) {
        if (activity == null)
            return

        val message = activity.getString(resId)
        showLong(activity, message, *args)
    }

    /**
     * Show message in [Toast] with [Toast.LENGTH_SHORT] duration
     *
     * @param activity
     * @param resId
     * @param args
     */
    fun showShort(activity: Activity?, resId: Int,
                  vararg args: Any) {
        if (activity == null)
            return

        val message = activity.getString(resId)
        showShort(activity, message, *args)
    }
}