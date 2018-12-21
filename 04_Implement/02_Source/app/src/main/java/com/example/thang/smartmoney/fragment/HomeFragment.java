package com.example.thang.smartmoney.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.Fragment_TransListByTime_Adapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private View view;
    private ViewPager mViewPager;
    private Fragment_TransListByTime_Adapter adapter;
    private Calendar calender;
    private TextView soDu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calender = Calendar.getInstance();
        DBVi.init(getContext());
        DBGiaoDich.init(getContext());
        adapter = new Fragment_TransListByTime_Adapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa();

        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);

        return view;
    }

    public void updateChangedData() {
//        adapter.notifyDataSetChanged();
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

        soDu = view.findViewById(R.id.home_sodu);
        soDu.setText(PriceFormat.format(DBVi.getSoDu()));
    }
}
