package com.example.thang.smartmoney.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thang.smartmoney.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private View view;
    private ViewPager mViewPager;
    private Fragment_TransListByTime_Adapter adapter;
    private Calendar calender;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calender = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa();

        adapter = new Fragment_TransListByTime_Adapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);

        return view;
    }

    private void AnhXa() {
        mViewPager = (ViewPager) view.findViewById(R.id.frag_home_viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pos = mViewPager.getCurrentItem();
                    Log.d("scroll", pos + "");
                    switch (pos) {
                        case 0:
                            calender.add(Calendar.DATE, -1);
                            break;
                        case 2:
                            calender.add(Calendar.DATE, 1);
                            break;
                    }
                    mViewPager.setCurrentItem(1, false);
                    adapter.setmCal(calender);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private class Fragment_TransListByTime_Adapter extends FragmentStatePagerAdapter {

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
}
