package com.example.thang.smartmoney.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thang.smartmoney.Activity_TietKiem_NganSach;
import com.example.thang.smartmoney.Activity_Tiet_Kiem;
import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.activity_budget;

public class fragment_tietkiem_ngansach extends Fragment {

    View view;
    Button tk_ns_btn_tietkiem;
    Button nganSachButton;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tietkiem_ngansach, null);
        AnhXa();
        return view;
    }

    void AnhXa(){
        tk_ns_btn_tietkiem = view.findViewById(R.id.saving_button);
        tk_ns_btn_tietkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , Activity_Tiet_Kiem.class);
                startActivity(intent);
            }
        });

        nganSachButton = view.findViewById(R.id.budget_button);
        nganSachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_budget.class);
                startActivity(intent);
            }
        });
    }
}
