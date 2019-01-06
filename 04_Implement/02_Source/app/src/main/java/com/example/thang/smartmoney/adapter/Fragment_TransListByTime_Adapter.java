package com.example.thang.smartmoney.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thang.smartmoney.fragment.Fragment_TransListByTime;

import java.util.Calendar;

public class Fragment_TransListByTime_Adapter extends FragmentStatePagerAdapter {

    private Calendar mCal;

    public Fragment_TransListByTime_Adapter(FragmentManager fm) {
        super(fm);
        mCal = Calendar.getInstance();
    }

    public void setmCal(Calendar mCal) {
        this.mCal = mCal;
    }

    @Override
    public Fragment getItem(int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(mCal.getTime());
        cal.add(Calendar.DATE, i - 1);

        Fragment_TransListByTime frag = new Fragment_TransListByTime();
        frag.setTime(cal.getTime());
        return frag;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }
}