package com.example.thang.smartmoney;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

import java.util.Date;

public class activity_budget_add extends AppCompatActivity
    implements View.OnClickListener
{
    Toolbar toolbar;
    TextInputEditText name;
    mPriceInput sotien;
    mDatePickerClick ngayBD, ngayKT;
    AppCompatButton addButton, xoaButton;

    ClassNganSach info;
    int info_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info_id = getIntent().getIntExtra("id", -1);
        if (info_id > -1) {
            info = DBNganSach.getInstance(this).getNganSach(info_id);
        }

        setContentView(R.layout.activity_budget_add);
        AnhXa();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addButton.setOnClickListener(this);
        if (info_id > -1) {
            xoaButton.setVisibility(View.VISIBLE);
            xoaButton.setOnClickListener(this);
            addButton.setText(R.string.update_title);
        }
    }

    void AnhXa()
    {
        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.name);
        sotien = new mPriceInput(this, R.id.sotien);
        ngayBD = new mDatePickerClick(this, R.id.ngay_bd);
        ngayKT = new mDatePickerClick(this, R.id.ngay_kt);
        addButton = findViewById(R.id.addBtn);
        xoaButton = findViewById(R.id.xoaBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addBtn:
                if (info_id > -1) addNganSach();
                else updateNganSach();
                break;

            case R.id.xoaBtn:
                xoaNganSach();
                break;
        }
    }

    void addNganSach()
    {
        if (!validateInput()) return;
        ClassNganSach nganSach = ClassNganSach.create(name.getText().toString(), sotien.getPrice(), ngayBD.getDate(), ngayKT.getDate());
        int id = DBNganSach.getInstance(this).create(nganSach);
        if (id > 0) {
            showInvalidInput("Them thanh cong");
            finish();
        }
    }

    void updateNganSach()
    {

    }

    void xoaNganSach()
    {

    }

    boolean validateInput()
    {
        String nameValue = name.getText().toString();
        if (nameValue.equals("") || nameValue.matches("\\s+")) {
            showInvalidInput("Ban chua nhap ten");
            return false;
        }

        int soTien = sotien.getPrice();
        if (soTien <= 1000 || soTien % 500 != 0) {
            showInvalidInput("So tien khong hop le");
            return false;
        }

        Date ngayBDValue = ngayBD.getDate();
        Date ngayKTValue = ngayKT.getDate();
        if (ngayBDValue.after(ngayKTValue)) {
            showInvalidInput("ngay khong hop le");
            return false;
        }

        return true;
    }

    void showInvalidInput(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
