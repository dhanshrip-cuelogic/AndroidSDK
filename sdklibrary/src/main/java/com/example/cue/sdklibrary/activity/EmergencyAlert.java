package com.example.cue.sdklibrary.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cue.sdklibrary.R;

public class EmergencyAlert  extends LinearLayout {
  private  Context context;
  private  OnEmergencyClickListioner onEmergencyClickListioner;
   public EmergencyAlert(Context context) {
        super(context);
        this.context= context;
        init();

    }
    public EmergencyAlert(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
       init();
    }

    public void init(){
    LayoutInflater inflater = LayoutInflater.from(getContext());
    inflater.inflate(R.layout.emergency, this);
    findViewById(R.id.btnEmergencyAcknowledge).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
           onEmergencyClickListioner.onClick();
        }
    });
   }

   public interface  OnEmergencyClickListioner {
      public void onClick();
   }
   public void setOnEmergencyAlert(OnEmergencyClickListioner onEmergencyAlert){
       this.onEmergencyClickListioner = onEmergencyAlert;
   }
}
