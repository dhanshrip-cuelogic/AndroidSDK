package com.example.cue.sdkapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loner.android.sdk.core.Loner;
import com.loner.android.sdk.model.response.AccessToken;
import com.loner.android.sdk.utilities.Toaster;
import com.loner.android.sdk.webservice.interfaces.ActivityCallBackInterface;
import com.loner.android.sdk.webservice.models.BaseData;
import com.loner.android.sdk.webservice.network.helper.NetworkErrorInformation;

public class RegistrationAcitivty extends AppCompatActivity {

    private static final String TAG = RegistrationAcitivty.class.getSimpleName();

    EditText mActivationEditText;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_acitivty);
        mActivationEditText = findViewById(R.id.ActivationEditText);
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we are calling sdk methods
//                Loner.getClient().Api().setRegistrationDevice(mActivationEditText.getText().toString().trim(), new ResponseStatusListener() {
//                    @Override
//                    public void onRequestSuccess(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onRequestFailure(String FailureMsg) {
//                        Toast.makeText(getApplicationContext(), FailureMsg, Toast.LENGTH_LONG).show();
//                    }
//                });


                String accessToken = mActivationEditText.getText().toString().trim();
                Loner loner = Loner.getClient();
                loner.register(accessToken, new ActivityCallBackInterface() {
                    @Override
                    public void onResponseDataSuccess(BaseData baseData) {
                        AccessToken accessToken = (AccessToken) baseData;
                        Log.e(TAG, "" + accessToken);
                        Toaster.showShort(RegistrationAcitivty.this, "" + accessToken);
                    }

                    @Override
                    public void onResponseDataFailure(NetworkErrorInformation errorInformation) {
                        Log.e(TAG, "" + errorInformation);
                        Toaster.showShort(RegistrationAcitivty.this, "" + errorInformation);
                    }
                });
            }

        });

    }
}
