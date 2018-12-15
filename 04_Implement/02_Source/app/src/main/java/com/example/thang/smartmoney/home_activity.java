package com.example.thang.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.thang.smartmoney.fragment.AddFragment;
import com.example.thang.smartmoney.fragment.CategoryListFragment;
import com.example.thang.smartmoney.fragment.HomeFragment;
import com.example.thang.smartmoney.fragment.RecommandFragment;
import com.example.thang.smartmoney.fragment.UserFragment;
import com.example.thang.smartmoney.fragment.fragment_thongke;

public class home_activity extends AppCompatActivity
    implements TabLayout.OnTabSelectedListener,
        View.OnClickListener
{

//    ImageButton imgbtnhome1, imgbtnadd1, imgbtnrecom1, imgbtnuser1, btnCategory;

    final int ADD_RESULT_CODE = 0;

    // fragment position
    final int FRAGMENT_HOME = 0;
    final int FRAGMENT_THONGKE = 1;
    final int FRAGMENT_DISABLED = 2;
    final int FRAGMENT_SAVING = 3;
    final int FRAGMENT_USER = 4;

    TabLayout tabLayout;
    ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

//        imgbtnhome1 = findViewById(R.id.imgbtnhome);
//        imgbtnadd1 = findViewById(R.id.imgbtnadd);
//        imgbtnrecom1 = findViewById(R.id.imgbtnrecom);
//        imgbtnuser1 = findViewById(R.id.imgbtnuser);
//        btnCategory = findViewById(R.id.btnCategory);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(this);

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
        switchButtonDefault();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        switch (pos) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case FRAGMENT_THONGKE:
                fragment = new fragment_thongke();
                break;
            case FRAGMENT_SAVING:
                break;
            case FRAGMENT_USER:
                fragment = new UserFragment();
                break;

        }

        if (fragment != null) {
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
                startActivityForResult(new Intent(getBaseContext(), ThemGiaoDich.class), ADD_RESULT_CODE);
                break;
        }
    }

    void switchButtonDefault() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case ADD_RESULT_CODE:
                    tabLayout.getTabAt(FRAGMENT_HOME).select();
                    break;
            }
        } catch (Exception e) {
            Log.d("ignore-error", e.getMessage());
            // ignore this error
        }
    }

}
