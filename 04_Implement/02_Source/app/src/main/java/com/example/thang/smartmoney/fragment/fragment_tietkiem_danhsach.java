package com.example.thang.smartmoney.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.SavingTransactionList;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBTietKiem;
import com.example.thang.smartmoney.edit_giaodich;
import com.example.thang.smartmoney.model.ClassTietKiem;

import java.util.Date;

public class fragment_tietkiem_danhsach extends Fragment
{
    View view;
    ListView listView;
    SavingTransactionList adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tietkiem_danhsach, container, false);
        AnhXa();

        adapter = new SavingTransactionList(getActivity());
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.delete, 0, R.string.delete_title);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int selectedId = (int)adapter.getItemId(info.position);

        switch (item.getItemId())
        {
            case R.id.delete:
                xoaTietKiem(selectedId);
                break;
        }

        return super.onContextItemSelected(item);
    }

    void AnhXa()
    {
        listView = view.findViewById(R.id.list_view);
    }

    void xoaTietKiem(final int gdId)
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_remove);

        Button xoa = dialog.findViewById(R.id.btnXoadgl);
        Button cancel = dialog.findViewById(R.id.btnHUYdgl);

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = DBTietKiem.xoaTietKiem(gdId);
                if (success) {
                    Toast.makeText(getContext(),
                            "Xoa thanh cong",
                            Toast.LENGTH_SHORT)
                            .show();

                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(),
                            getString(R.string.error_message) + " xoa that bai",
                            Toast.LENGTH_SHORT)
                            .show();
                    dialog.cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}