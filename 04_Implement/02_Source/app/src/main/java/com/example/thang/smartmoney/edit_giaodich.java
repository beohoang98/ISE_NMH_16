package com.example.thang.smartmoney;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

public class edit_giaodich extends AppCompatActivity {

    Toolbar toolbar;
    mPriceInput priceInput;
    mDatePickerClick datePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_giaodich);
        AnhXa();
    }

    void AnhXa()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        priceInput = new mPriceInput(this, R.id.edit_price);
        datePicker = new mDatePickerClick(this, R.id.edit_date);
    }

}
