package com.example.cue.sdkapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cue.sdklibrary.Loner;
import com.example.cue.sdklibrary.apiModel.ResponseStatusListener;

public class RegistrationAcitivty extends AppCompatActivity {

    EditText mActivationEditText;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_acitivty);
        mActivationEditText = findViewById(R.id.ActivationEditText);
        mLoginButton =  findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we are calling sdk methods
                Loner.getClient().Api().setRegistrationDevice(mActivationEditText.getText().toString().trim(), new ResponseStatusListener() {
                     @Override
                     public void onRequestSuccess(String response) {
                         Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
                     }

                     @Override
                     public void onRequestFailure(String FailureMsg) {
                         Toast.makeText(getApplicationContext(), FailureMsg,Toast.LENGTH_LONG).show();
                     }
                 });
             }

        });

    }
}
