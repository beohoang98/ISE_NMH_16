package com.example.thang.smartmoney.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.DBTietKiem;
import com.example.thang.smartmoney.model.ClassVi;
import com.example.thang.smartmoney.xulysukien.PriceFormat;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

import java.util.Calendar;
import java.util.Date;

public class fragment_tietkiem_tien extends Fragment
    implements View.OnClickListener
{
    View view;
    Button addButton;
    TextView soDuView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBTietKiem.init(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tietkiem_tien, container, false);
        addButton = view.findViewById(R.id.add_saving_button);
        addButton.setOnClickListener(this);

        soDuView = view.findViewById(R.id.tietkiem_tien_textview_tien);
        refreshData();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_saving_button:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_tietkiem_them);
                Button okButton = dialog.findViewById(R.id.addBtn);
                final mPriceInput tienInput = new mPriceInput(dialog, R.id.sotien);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soTien = tienInput.getPrice();
                        if (!themTietKiem(soTien)) {
                            Toast.makeText(getContext(), R.string.price_le, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
                        refreshData();
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
        }
    }

    boolean themTietKiem(int sotien) {
        if (sotien < 1000 || sotien % 500 > 0) return false;

        Date date = Calendar.getInstance().getTime(); // now
        DBTietKiem.themTietKiem(date, sotien, ClassVi.VI_TIET_KIEM, "");

        return true;
    }

    void refreshData()
    {
        int soDu = DBTietKiem.getSoDu(ClassVi.VI_TIET_KIEM);
        soDuView.setText(PriceFormat.format(soDu));
    }
}

