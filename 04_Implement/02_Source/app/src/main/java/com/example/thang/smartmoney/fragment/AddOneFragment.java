package com.example.thang.smartmoney.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.CategorySpinnerAdapter;
import com.example.thang.smartmoney.adapter.ListTransactionHomeAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassIncome;
import com.example.thang.smartmoney.xulysukien.KiemTraInput;
import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class AddOneFragment extends Fragment {

    View view;
    mDatePickerClick datePickerInput;
    mPriceInput priceInput;
    TextInputEditText noteText;

    MaterialButton buttonAdd;

    ListTransactionHomeAdapter adapter;
    Calendar calendar;
    AppCompatSpinner categorySpinner;
    CategorySpinnerAdapter categorySpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_income, container, false);

        AnhXa();
        // MainActivity.database.QueryData("CREATE TABLE IF NOT EXISTS GhiChuThuNhap(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenTC VARCHAR,DateTC VARCHAR)");
        //
//        GetDataGhiChu();
//        btnAddGhiChu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String date=
//                String TextGhiChu=edtAddGhiChu.getText().toString();
//                if(TextGhiChu.equals("")){
//                    Toast.makeText(getActivity(), "vui long nhap ghi chu", Toast.LENGTH_SHORT).show();
//                }else {
//                    // MainActivity.database.QueryData("INSERT INTO GhichuThuNhap VALUES(null,'"+TextGhiChu+"','"+date+"')");
//                    MainActivity.mGhiChuDB.addGhiChu(TextGhiChu, date);
//
//                    GetDataGhiChu();
//                    edtAddGhiChu.setText("");
//                }
//            }
//        });
//        btnHUY.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                edtAddGhiChu.setText("");
//            }
//        });
//
//        LvAddIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                final Dialog dialog=new Dialog(getActivity());
//                dialog.setContentView(R.layout.dialog_add_remove);
//                Button btnXoadgl=dialog.findViewById(R.id.btnXoadgl);
//                Button btnHUYdgl=dialog.findViewById(R.id.btnHUYdgl);
//
//                btnXoadgl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ClassGiaoDich gc = ghiChuArrayList.get(position);
//                        DBGiaoDich.xoa(gc.id);
//                        dialog.dismiss();
//                        GetDataGhiChu();
//                    }
//                });
//
//                btnHUYdgl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//
//                return false;
//            }
//        });

        return view;
    }

    private void GetDataGhiChu() {
//        Cursor dataThuChi =MainActivity.database.GetData("SELECT * FROM GhiChuThuNhap");
//        ghiChuArrayList.clear();
//        while (dataThuChi.moveToNext())
//        {
//            String ten       = dataThuChi.getString(1);
//            int    id        = dataThuChi.getInt(0);
//            String datetime  = dataThuChi.getString(2);
//            ghiChuArrayList.add(0,new AddGhiChu(id,ten,datetime));
//        }
        adapter.notifyDataSetChanged();
    }

    public void AnhXa() {

//        LvAddIncome      = view.findViewById(R.id.LvAddIncome);
//        edtAddGhiChu     = view.findViewById(R.id.edtAddGHiChu);
//        btnThemTN        = view.findViewById(R.id.btnThemTN);
//        btnHUYTN         = view.findViewById(R.id.btnHUYTN);
//        btnAddGhiChu     = view.findViewById(R.id.btnAddGhiChu);
//        btnHUY           = view.findViewById(R.id.btnHUY);
        calendar = Calendar.getInstance();
        categorySpinner = view.findViewById(R.id.frag_income_spinner);
        categorySpinnerAdapter = new CategorySpinnerAdapter(getContext(), ClassCategory.CATEGORY_TYPE.INCOME);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        noteText = view.findViewById(R.id.frag_income_note);

        handlePriceInput();
        handleDateInput();
        handleSubmit();
    }

    private String getdate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhdang = new SimpleDateFormat("dd/MM/yyyy");
        String date = dinhdang.format(calendar.getTime());
        return date;
    }

//        private void Dialog_remove (){
//        final Dialog dialog=new Dialog(getActivity());
//        dialog.setContentView(R.layout.dialog_add_remove);
//        Button btnXoadgl=dialog.findViewById(R.id.btnXoadgl);
//        Button btnHUYdgl=dialog.findViewById(R.id.btnHUYdgl);
//
//        btnXoadgl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database.QueryData("DELETE FROM GhiChuThuNhap WHERE Id='"+  +"'");
//            }
//        });
//
//        btnHUYdgl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//
//
//    }


//    private void addGhiChu(){
//        AnhXa();
//
//        btnAddGhiChu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String date=getdate();
//                String TextGhiChu=edtAddGhiChu.getText().toString();
//                if(TextGhiChu.equals("")){
//                    Toast.makeText(getActivity(), "vui long nhap ghi chu", Toast.LENGTH_SHORT).show();
//                }else {
//                    database.QueryData("INSERT INTO GhichuThuNhap VALUES(null,'"+TextGhiChu+"','"+date+"')");
//                    GetDataGhiChu();
//                }
//            }
//        });
//    }

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

                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.detach(thisFrag).attach(thisFrag).commit();
                }
            }
        });
    }
}
