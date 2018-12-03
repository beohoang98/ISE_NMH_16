package com.example.thang.smartmoney;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTwoFragment extends Fragment {

    View view;
    ListView LvAddSpend;
    EditText edtAddGhiChu1;
    Button btnAddGhiChu1,btnHUY1,btnThemCT,btnHUYCT;
    ArrayList<AddGhiChu> ghiChuArrayList;
    AdapterAdd adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_spend,container,false);

        AnhXa();
        MainActivity.database.QueryData("CREATE TABLE IF NOT EXISTS GhiChuChiTieu1(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenCT VARCHAR,DateCT VARCHAR)");
        //
        GetDataGhiChu();
        btnAddGhiChu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=getdate();
                String TextGhiChu=edtAddGhiChu1.getText().toString();
                if(TextGhiChu.equals("")){
                    Toast.makeText(getActivity(), "vui long nhap ghi chu", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.database.QueryData("INSERT INTO GhichuChiTieu1 VALUES(null,'"+TextGhiChu+"','"+date+"')");
                    GetDataGhiChu();
                    edtAddGhiChu1.setText("");
                }
            }
        });
        btnHUY1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddGhiChu1.setText("");
            }
        });

        LvAddSpend.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_remove);
                Button btnXoadgl=dialog.findViewById(R.id.btnXoadgl);
                Button btnHUYdgl=dialog.findViewById(R.id.btnHUYdgl);

                btnXoadgl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddGhiChu gc=ghiChuArrayList.get(position);
                        int i=gc.getId();
                        MainActivity.database.QueryData("DELETE FROM GhiChuChiTieu1 WHERE Id='"+ i +"'");
                        dialog.dismiss();
                        GetDataGhiChu();
                    }
                });

                btnHUYdgl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return false;
            }
        });

        return view;
    }

    private void GetDataGhiChu(){
        Cursor dataThuChi =MainActivity.database.GetData("SELECT * FROM GhiChuChiTieu1");
        ghiChuArrayList.clear();
        while (dataThuChi.moveToNext())
        {
            String ten       = dataThuChi.getString(1);
            int    id        = dataThuChi.getInt(0);
            String datetime  = dataThuChi.getString(2);
            ghiChuArrayList.add(0,new AddGhiChu(id,ten,datetime));
        }
        adapter.notifyDataSetChanged();
    }

    public void AnhXa(){

        LvAddSpend       = view.findViewById(R.id.LvAddSpend);
        edtAddGhiChu1    = view.findViewById(R.id.edtAddGhiChu1);
        btnThemCT        = view.findViewById(R.id.btnThemCT);
        btnHUYCT         = view.findViewById(R.id.btnHUYCT);
        btnAddGhiChu1    = view.findViewById(R.id.btnAddGhiChu1);
        btnHUY1          = view.findViewById(R.id.btnHUY1);
        ghiChuArrayList  = new ArrayList<>();
        adapter          = new AdapterAdd(getActivity(),R.layout.custom_dong_ghichu,ghiChuArrayList);
        LvAddSpend.      setAdapter(adapter);
    }

    private String getdate(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dinhdang=new SimpleDateFormat("dd/MM/yyyy");
        String date=dinhdang.format(calendar.getTime());
        return date;
    }
}
