package com.example.thang.smartmoney;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btnaddincome;
    Button btnaddspend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add,container,false);

        btnaddincome=view.findViewById(R.id.btnaddincome);
        btnaddspend=view.findViewById(R.id.btnaddspend);
        btnaddincome.setOnClickListener(this);
        btnaddspend.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment=null;

        switch (v.getId())
        {
            case R.id.btnaddincome: fragment=new AddOneFragment();
                btnaddincome.setBackgroundColor(getResources().getColor(R.color.color_blue));
                btnaddincome.setTextColor(getResources().getColor(R.color.color_while));
                btnaddspend.setBackgroundColor(getResources().getColor(R.color.color_while));
                btnaddspend.setTextColor(getResources().getColor(R.color.color_blue));
                break;

            case R.id.btnaddspend: fragment=new AddTwoFragment();
                btnaddincome.setBackgroundColor(getResources().getColor(R.color.color_while));
                btnaddincome.setTextColor(getResources().getColor(R.color.color_blue));
                btnaddspend.setBackgroundColor(getResources().getColor(R.color.color_blue));
                btnaddspend.setTextColor(getResources().getColor(R.color.color_while));
                break;
        }
        fragmentTransaction.add(R.id.framecontentadd,fragment);
        fragmentTransaction.commit();

    }
}

