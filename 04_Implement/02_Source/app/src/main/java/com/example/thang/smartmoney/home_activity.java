package com.example.thang.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.fragment.HomeFragment;
import com.example.thang.smartmoney.fragment.UserFragment;
import com.example.thang.smartmoney.fragment.fragment_thongke;
import com.example.thang.smartmoney.fragment.fragment_tietkiem_ngansach;

public class home_activity extends AppCompatActivity
    implements TabLayout.OnTabSelectedListener,
        View.OnClickListener
{
    public static final int ADD_REQUEST_CODE = 0;
    public static final int EDIT_REQUEST_CODE = 1;

    // fragment position
    final int FRAGMENT_HOME = 0;
    final int FRAGMENT_THONGKE = 1;
    final int FRAGMENT_DISABLED = 2;
    final int FRAGMENT_SAVING_BUDGET = 3;
    final int FRAGMENT_USER = 4;

    int stateResume = -1;

    TabLayout tabLayout;
    ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(this);

        // init database
        Database.getInstance(getBaseContext());

        // disable center tab
        ((ViewGroup)tabLayout.getChildAt(0))
                .getChildAt(FRAGMENT_DISABLED)
                .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
    }

    public void switchToPage(int pos) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        switch (pos) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                ((HomeFragment)fragment).updateChangedData();
                break;
            case FRAGMENT_THONGKE:
                fragment = new fragment_thongke();
                break;
            case FRAGMENT_SAVING_BUDGET:
                fragment = new fragment_tietkiem_ngansach();
                break;
            case FRAGMENT_USER:
                fragment = new UserFragment();
                break;

        }

        if (fragment != null) {
            fragmentManager.popBackStack();
            fragmentTransaction.replace(R.id.framecontent, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switchToPage(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switchToPage(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                startActivityForResult(new Intent(getBaseContext(), Activity_ThemGiaoDich.class), ADD_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.stateResume == ADD_REQUEST_CODE || this.stateResume == EDIT_REQUEST_CODE) {
            tabLayout.getTabAt(FRAGMENT_HOME).select();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode > 0) this.stateResume = requestCode;
    }

}
