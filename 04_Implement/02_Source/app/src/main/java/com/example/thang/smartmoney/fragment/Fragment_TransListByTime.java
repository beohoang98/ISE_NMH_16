package com.example.thang.smartmoney.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.ListTransactionHomeAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.edit_giaodich;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.DateFormat;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Fragment_TransListByTime extends Fragment
    implements ListView.OnItemClickListener
{
    private View view;
    private ListView listView;
    private TextView titleView;

    private TextView sumIncomeText;
    private TextView sumOutcomeText;
    private TextView sumTotalText;

    private ArrayList<ClassGiaoDich> listTransactions;
    private ListTransactionHomeAdapter adapter;
    private String dateStr;
    private Date date;

    public void setTime(Date date) {
        this.dateStr = DateFormat.format(date);
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaction_list_by_time, container, false);
        DBVi.init(view.getContext());

        AnhXa();

        showData();
        return view;
    }

    void AnhXa() {
        titleView = (TextView) view.findViewById(R.id.frag_trans_list_date_title);
        listView = (ListView) view.findViewById(R.id.frag_trans_list_listview);
        sumIncomeText = view.findViewById(R.id.sumIncome);
        sumOutcomeText = view.findViewById(R.id.sumOutcome);
        sumTotalText = view.findViewById(R.id.sumTotal);

        listTransactions = new ArrayList<>();

        adapter = new ListTransactionHomeAdapter(getActivity(), listTransactions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Calendar thisDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        thisDate.setTime(date);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        Calendar yesterday = (Calendar)now.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        if (thisDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            this.dateStr = getContext().getString(R.string.date_today);
        } else if (thisDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            this.dateStr = getContext().getString(R.string.date_yesterday);
        }
    }

    void showData() {
        int tongIncome = DBVi.getTongIncomeByDate(date);
        int tongOutcome = DBVi.getTongOutcomeByDate(date);
        int tong = tongIncome - tongOutcome;

        titleView.setText(dateStr);
        sumIncomeText.setText("+ " + PriceFormat.format(tongIncome));
        sumOutcomeText.setText("- " + PriceFormat.format(tongOutcome));
        sumTotalText.setText(PriceFormat.format(tong));
        if (tong >= 0) {
            sumTotalText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.income));
        } else {
            sumTotalText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.expense));
        }

        listTransactions.clear();
        listTransactions.addAll(0, DBGiaoDich.getByDate(date));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivityForResult(new Intent(getActivity(), edit_giaodich.class), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
