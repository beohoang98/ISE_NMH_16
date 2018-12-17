package com.example.thang.smartmoney.xulysukien;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.thang.smartmoney.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class mDatePickerClick implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Context mCtx;
    TextInputEditText editText;

    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar cal;
    private SimpleDateFormat fmt;

    public mDatePickerClick(View view, int editTextId)
    {
        mCtx = view.getContext();
        this.editText = view.findViewById(editTextId);
        this.editText.setOnClickListener(this);
        this.editText.setFocusable(false);

        fmt = new SimpleDateFormat(mCtx.getString(R.string.date_format));

        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // mac dinh la ngay hom nay
        editText.setText(fmt.format(cal.getTime()));
    }

    public mDatePickerClick(Activity activity, int editTextId) {
        this(((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0),
                editTextId);
    }

    public Date getDate()
    {
        return cal.getTime();
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dialog = new DatePickerDialog(mCtx, this,
                mYear, mMonth, mDay);

        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;

        cal.set(mYear, mMonth, mDay);

        editText.setText(fmt.format(cal.getTime()));
    }
}
