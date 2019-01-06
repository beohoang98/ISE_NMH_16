package com.example.thang.smartmoney.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.CategorySpinnerAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassIncome;
import com.example.thang.smartmoney.xulysukien.KiemTraInput;
import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;
import java.util.Calendar;
import java.util.Date;

public class AddOneFragment extends Fragment {

    View view;
    mDatePickerClick datePickerInput;
    mPriceInput priceInput;
    TextInputEditText noteText;

    Button buttonAdd;

    Calendar calendar;
    AppCompatSpinner categorySpinner;
    CategorySpinnerAdapter categorySpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_income, container, false);

        AnhXa();
        return view;
    }


    public void AnhXa() {
        calendar = Calendar.getInstance();
        categorySpinner = view.findViewById(R.id.frag_income_spinner);
        categorySpinnerAdapter = new CategorySpinnerAdapter(getActivity(), ClassCategory.CATEGORY_TYPE.INCOME);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setPrompt(getString(R.string.category_title));

        noteText = view.findViewById(R.id.frag_income_note);

        handlePriceInput();
        handleDateInput();
        handleSubmit();
    }

    void handlePriceInput()
    {
        priceInput = new mPriceInput(view, R.id.frag_income_price);
    }

    void handleDateInput()
    {
        datePickerInput = new mDatePickerClick(view, R.id.frag_income_date);
    }

    void handleSubmit()
    {
        final Context _ctx = getContext();
        final Fragment thisFrag = this;
        buttonAdd = view.findViewById(R.id.frag_income_submit);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiemTraInput validator = new KiemTraInput(_ctx);

                Date date = datePickerInput.getDate();
                int price = priceInput.getPrice();
                String note = noteText.getText().toString();
                int category_id = (int)categorySpinner.getSelectedItemId();

                if (validator.KiemTraGiaTien(price) && validator.KiemTraCategory(category_id)) {
                    ClassIncome income = new ClassIncome(date, price, category_id, note);
                    DBGiaoDich.them(income);
                    Toast.makeText(_ctx, "Success", Toast.LENGTH_SHORT).show();

                    getActivity().setResult(1);
                    getActivity().finish();
                }
            }
        });
    }
}
