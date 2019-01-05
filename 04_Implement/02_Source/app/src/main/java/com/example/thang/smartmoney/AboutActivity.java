package com.example.thang.smartmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.thang.smartmoney.fragment.UserFragment;

public class AboutActivity extends AppCompatActivity {

    private ImageButton back2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        back2Button = view.findViewById(R.id.imageButton);
        back2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUser();
            }
        });
    }
    public void openUser(){
        Intent intent = new Intent(this, UserFragment.class);
        startActivity(intent);
    }
}
