package com.example.thang.smartmoney.xulysukien;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class mPriceInput implements TextWatcher {

    private TextInputEditText editText;
    private Context ctx;
    private NumberFormat fmt;

    public mPriceInput(View view, int inputId)
    {
        ctx = view.getContext();
        editText = view.findViewById(inputId);
        editText.addTextChangedListener(this);
        fmt = new DecimalFormat("#,###");
    }

    public int getPrice()
    {
        int valueNum = -1;
        try {
            String val = editText.getText().toString().replaceAll("\\D+", "");
            valueNum = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            Log.w("getprice", e.getMessage());
        }

        return valueNum;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String val = s.toString();
        editText.removeTextChangedListener(this);
        try {
            val = val.replaceAll("\\D+", "");
            int valueNum = Integer.parseInt(val);
            val = fmt.format(valueNum);
        } catch (NumberFormatException e) {
            val = s.toString();
        }
        editText.setText(val);
        editText.setSelection(val.length());
        editText.addTextChangedListener(this);
    }
}
