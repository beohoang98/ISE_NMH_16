package com.example.thang.smartmoney;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.thang.smartmoney.fragment.AddFragment;
import com.example.thang.smartmoney.fragment.HomeFragment;
import com.example.thang.smartmoney.fragment.RecommandFragment;
import com.example.thang.smartmoney.fragment.UserFragment;

public class home_activity extends AppCompatActivity {

    ImageButton imgbtnhome1, imgbtnadd1, imgbtnrecom1, imgbtnuser1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
    }

    public void addfragment(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        imgbtnhome1 = findViewById(R.id.imgbtnhome);
        imgbtnadd1 = findViewById(R.id.imgbtnadd);
        imgbtnrecom1 = findViewById(R.id.imgbtnrecom);
        imgbtnuser1 = findViewById(R.id.imgbtnuser);

        switch (view.getId()) {
            case R.id.imgbtnhome:
                fragment = new HomeFragment();
                imgbtnhome1.setImageResource(R.drawable.home_on);
                imgbtnadd1.setImageResource(R.drawable.add_off);
                imgbtnrecom1.setImageResource(R.drawable.recom_off);
                imgbtnuser1.setImageResource(R.drawable.user_off);
                break;
            case R.id.imgbtnadd:
                fragment = new AddFragment();
                imgbtnhome1.setImageResource(R.drawable.home_off);
                imgbtnadd1.setImageResource(R.drawable.add_on);
                imgbtnrecom1.setImageResource(R.drawable.recom_off);
                imgbtnuser1.setImageResource(R.drawable.user_off);
                break;
            case R.id.imgbtnrecom:
                fragment = new RecommandFragment();
                imgbtnhome1.setImageResource(R.drawable.home_off);
                imgbtnadd1.setImageResource(R.drawable.add_off);
                imgbtnrecom1.setImageResource(R.drawable.recom_on);
                imgbtnuser1.setImageResource(R.drawable.user_off);
                break;
            case R.id.imgbtnuser:
                fragment = new UserFragment();
                imgbtnhome1.setImageResource(R.drawable.home_off);
                imgbtnadd1.setImageResource(R.drawable.add_off);
                imgbtnrecom1.setImageResource(R.drawable.recom_off);
                imgbtnuser1.setImageResource(R.drawable.user_on);
                break;
        }

        fragmentTransaction.add(R.id.framecontent, fragment);
        fragmentTransaction.commit();
    }
}
