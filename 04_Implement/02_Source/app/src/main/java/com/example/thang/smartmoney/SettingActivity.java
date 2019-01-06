package com.example.thang.smartmoney;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.thang.smartmoney.notification.NotifyService;

public class SettingActivity extends AppCompatActivity
    implements CompoundButton.OnCheckedChangeListener
{
    NotifyService notifyService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        notifyService = new NotifyService(this);

        SwitchCompat switchCompat = findViewById(R.id.switch_notify);
        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            notifyService.turnOn(16, 0, 0);
        } else {
            notifyService.turnOff();
        }
    }
}
