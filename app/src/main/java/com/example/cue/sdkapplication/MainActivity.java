package com.example.cue.sdkapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loner.android.sdk.activity.EmergencyAlert;
import com.loner.android.sdk.utilities.Toaster;


public class MainActivity extends AppCompatActivity {
    EmergencyAlert emergencyAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emergencyAlert = findViewById(R.id.emergency);
        emergencyAlert.setOnEmergencyAlert(new EmergencyAlert.OnEmergencyClickListioner() {
            @Override
            public void onClick() {
                Toaster.showShort(MainActivity.this, "This is Emergency Alert");
            }
        });


    }
}
