package com.example.thang.smartmoney;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

import java.util.zip.Inflater;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        priceInput = new mPriceInput(this, R.id.edit_price);
        datePicker = new mDatePickerClick(this, R.id.edit_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_giaodich, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:

                Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
