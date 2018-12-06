package com.example.thang.smartmoney.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.ListTransactionHomeAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassGiaoDich;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_TransListByTime extends Fragment {
    private View view;
    private ListView listView;
    private TextView titleView;

    private ArrayList<ClassGiaoDich> listTransactions;
    private ListTransactionHomeAdapter adapter;
    private String dateStr;
    private Date date;

    public void setTime(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        this.dateStr = fmt.format(date);
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaction_list_by_time, container, false);
        AnhXa();

        showData();
        return view;
    }

    void AnhXa() {
        titleView = (TextView) view.findViewById(R.id.frag_trans_list_date_title);
        listView = (ListView) view.findViewById(R.id.frag_trans_list_listview);
        listTransactions = new ArrayList<>();

        adapter = new ListTransactionHomeAdapter(getActivity(), listTransactions);

        listView.setAdapter(adapter);
    }

    void showData() {
        titleView.setText(dateStr);

        listTransactions.clear();
        listTransactions.addAll(0, DBGiaoDich.getByDate(date));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
