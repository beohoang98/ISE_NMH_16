package com.example.thang.smartmoney.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thang.smartmoney.R;

public class AddFragment extends Fragment
//        implements View.OnClickListener
{

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        viewPager = view.findViewById(R.id.frag_add_pager);
        AddFramentPagerAdapter adapter = new AddFramentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.frag_add_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public class AddFramentPagerAdapter extends FragmentStatePagerAdapter {
        final int TAB_NUM = 2;
        final int[] titleId = { R.string.income_title, R.string.expense_title};
        FragmentManager mFm;

        public AddFramentPagerAdapter(FragmentManager fm)
        {
            super(fm);
            mFm = fm;
        }

        @Override
        public int getCount() {
            return TAB_NUM;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(titleId[position]);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment frag = null;

            if (i == 0) {
                frag = new AddOneFragment();
            } else {
                frag = new AddExpenseFragment();
            }

            return frag;
        }
    }
}

