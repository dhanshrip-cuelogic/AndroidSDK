package com.loner.android.sdk.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.loner.android.sdk.dailogs.LonerDialog
import com.loner.android.sdk.dailogs.LonerDialogListener
import com.loner.android.sdk.model.respons.LonerPermission
import com.loner.android.sdk.utils.Constant
import java.util.ArrayList
import java.util.HashMap


class PermissionActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private var permissionMap = HashMap<String, String>()
    private var listPermissionsNeeded = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionMap[Manifest.permission.ACCESS_FINE_LOCATION] = "Loner Mobile uses your location to ensure you get the help you need."
        //permissionMap[Manifest.permission.CALL_PHONE] = "Loner Mobile uses your call settings to ensure you get the help you need."
        if (Build.VERSION.SDK_INT >= 23) {
            if (isPermissionsGranted()) {
                onPermissionsGranted()
            } else {
                requestPermissions(permissionMap, Constant.PERMISSION_REQUEST_CODE)
            }
        } else {
            onPermissionsGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()) {
                val perms = HashMap<String, Int>()

                for (i in permissions.indices) {
                    perms[permissions[i]] = grantResults[i]
                }

                val pendingPermissions = ArrayList<String>()

                for (i in listPermissionsNeeded.indices) {
                    if (perms[listPermissionsNeeded[i]] != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, listPermissionsNeeded[i])) {
                            pendingPermissions.add(listPermissionsNeeded[i])
                        } else {
                            showSettingAlertMessage("Allow permission", permissionMap[listPermissionsNeeded[i]])
                            return
                        }
                    }
                }
                if (pendingPermissions.size > 0) {
                    requestPermissions(permissionMap, requestCode)
                } else {
                    onPermissionsGranted()
                }
            }

        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.PERMISSION_REQUEST_CODE) {
            if(isPermissionsGranted())
                onPermissionsGranted()
            else {
                requestPermissions(permissionMap, requestCode)
            }
        }else if (requestCode == Constant.REQUEST_CHECK_SETTINGS) {
            //Check the result from location setting request
            if (resultCode == Activity.RESULT_OK) {
                //User enabled GPS
                this.finish()
                LonerPermission.onPermissionGranted()
            }else{
                showLocationSettingAlertMessage("Error", "Location service is disabled please enable it from setting")
            }
        }
    }

    /**
     * Request permissions to the OS
     * @param permissionsMap Hashmap of permissions
     * @param requestCode Int request code of permission
     */
    private fun requestPermissions(permissionsMap: HashMap<String, String>, requestCode: Int) {
        if (permissionsMap.size > 0) {
            if (listPermissionsNeeded.isNotEmpty()) {
                listPermissionsNeeded.clear()
            }

            for (permission in permissionsMap.keys) {
                val hasPermission = ContextCompat.checkSelfPermission(this, permission)
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permission)
                }
            }

            if (listPermissionsNeeded.isNotEmpty()) {
                if (permissionMap.size == listPermissionsNeeded.size) {
                    showPermissionAlertMessage()
                } else {
                    ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), requestCode)
                }
            }
        }
    }

    /**
     * Verfies all the permissions are granted
     */
    private fun isPermissionsGranted(): Boolean {
        for (permission in permissionMap.keys) {
            val permissionState = PermissionChecker.checkSelfPermission(this, permission)
            if (permissionState != PermissionChecker.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    /**
     * Redirect user to App settings screen to grant permissions
     */
    private fun goToAppPermissionScreen() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", this.getPackageName(), null)
        intent.data = uri
        startActivityForResult(intent, Constant.PERMISSION_REQUEST_CODE)
    }

    /**
     * Show Alert dialog to show usage of permission
     * @param message String indicates the usage of permission
     */
    private fun showSettingAlertMessage(title: String, message: String?) {
        if(!isFinishing && !LonerDialog.getInstance().isShowingAlert()) {
            LonerDialog.getInstance().showAlertDialog(this, title, message, null, object : LonerDialogListener {
                override fun onPositiveButtonClicked() {
                    goToAppPermissionScreen()
                }
            })
        }
    }

    /**
     * Show usage of each permission after user deny.
     */
    private fun showPermissionAlertMessage() {
        if(!isFinishing && !LonerDialog.getInstance().isShowingAlert()) {
            LonerDialog.getInstance().showAlertDialog(this, "Allow location and phone access", "Loner Mobile uses your location and call settings to ensure you get the help you need.", null, object : LonerDialogListener {
                override fun onPositiveButtonClicked() {
                    ActivityCompat.requestPermissions(this@PermissionActivity, listPermissionsNeeded.toTypedArray(), Constant.PERMISSION_REQUEST_CODE)
                }
            })
        }
    }


    /**
     * Show Alert dialog to show enable location
     * @param message String indicates the usage of permission
     */
    private fun showLocationSettingAlertMessage(title: String, message: String?) {
        if(!isFinishing && !LonerDialog.getInstance().isShowingAlert()) {
            LonerDialog.getInstance().showAlertDialog(this, title, message, null, object : LonerDialogListener {
                override fun onPositiveButtonClicked() {
                    checkGPSSettings()
                }
            })
        }
    }


    /**
     * Permission granted callback to the client application
     */
    private fun onPermissionsGranted(){
//        this.finish()
//        LonerPermission.onPermissionGranted()
        checkGPSSettings()
    }

    /**
     * check GPS settings status
     */
    private fun checkGPSSettings(){
        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // Location settings are satisdied,finish the permission activity and send callback to the client app
            this.finish()
            LonerPermission.onPermissionGranted()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@PermissionActivity,
                            Constant.REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }


}
