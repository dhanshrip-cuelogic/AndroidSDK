package com.example.cue.sdkapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.loner.android.sdk.core.Loner
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface

class RegistrationAcitivty : AppCompatActivity() {

    var mActivationEditText: EditText? = null
    var mLoginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_acitivty)
        mActivationEditText = findViewById(R.id.ActivationEditText)
        mLoginButton = findViewById(R.id.loginButton)
        mLoginButton?.setOnClickListener {
            val accessToken = mActivationEditText?.text.toString().trim { it <= ' ' }
            Loner.client.registerDeviceApi(accessToken, object : ActivityCallBackInterface {
                override fun onResponseDataSuccess(response: String) {
                    Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
                }

                override fun onResponseDataFailure(errorInformation: String) {
                    Toast.makeText(applicationContext, errorInformation, Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    companion object {

        private val TAG = RegistrationAcitivty::class.java.simpleName
    }
}
