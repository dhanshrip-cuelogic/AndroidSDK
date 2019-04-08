package com.example.cue.sdkapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cue.sdklibrary.activity.EmergencyAlert;
import com.example.cue.sdklibrary.Utility.ToastManger;


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
                ToastManger.showsMessage(MainActivity.this, "This is Emergency Alert");
            }
        });


    }
}
