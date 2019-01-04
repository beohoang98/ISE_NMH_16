package com.example.thang.smartmoney;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thang.smartmoney.adapter.ListTransactionHomeAdapter;
import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.database.DBTietKiem;
import com.example.thang.smartmoney.model.ClassExpense;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.xulysukien.DeleteDialog;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class activity_budget_info extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ClassNganSach> nganSach;
    DayByDayAdapter adapter;
    int nganSachId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);

        nganSachId = getIntent().getIntExtra("id", -1);
        if (nganSachId == -1) {
            finish();
            return;
        }
        nganSach = new ArrayList<>();
        nganSach.add(DBNganSach.getInstance(this).getNganSach(nganSachId));

        AnhXa();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(nganSach.get(0).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DayByDayAdapter(nganSach.get(0));
        recyclerView.setAdapter(adapter);
    }

    void AnhXa()
    {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_budget_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                editNganSach();
                break;
            case R.id.delete:
                xoaNganSach();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void editNganSach()
    {
        Intent intent = new Intent(this, activity_budget_add.class);
        intent.putExtra("id", nganSachId);
        startActivity(intent);
    }

    void xoaNganSach()
    {
        DeleteDialog deleteDialog = new DeleteDialog(this);
        deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirm() {
            @Override
            public void onConfirm() {
                int n = DBNganSach.getInstance(getBaseContext()).xoa(nganSachId);
                if (n <= 0) {
                    Toast.makeText(getBaseContext(), "Xoa loi", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    Toast.makeText(getBaseContext(), "Xoa thanh cong", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
            }
        });
        deleteDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nganSach.clear();
        nganSach.add(DBNganSach.getInstance(this).getNganSach(nganSachId));
        toolbar.setTitle(nganSach.get(0).getName());
        adapter.notifyDataSetChanged();
    }

    class DayByDayAdapter extends RecyclerView.Adapter<DayByDayAdapter.ViewHolder>
    {
        Map<String, ArrayList<ClassGiaoDich>> mapDay;
        List<String> mapKeys;
        Map<String, Integer> tongChiTieu;
        ClassNganSach data;

        public DayByDayAdapter(ClassNganSach data)
        {
            this.data = data;
            mapDay = new HashMap<>();
            tongChiTieu = new HashMap<>();
            refreshData();
        }

        void refreshData()
        {
            mapDay.clear();
            tongChiTieu.clear();
            List<ClassExpense> list = data.getChiTieu(activity_budget_info.this);
            for (ClassGiaoDich giaoDich : list) {
                String dateStr = giaoDich.thoigian;
                if (!mapDay.containsKey(dateStr)) mapDay.put(dateStr, new ArrayList<ClassGiaoDich>());
                mapDay.get(dateStr).add(giaoDich);

                if (!tongChiTieu.containsKey(dateStr)) tongChiTieu.put(dateStr, 0);
                tongChiTieu.put(dateStr, tongChiTieu.get(dateStr) + giaoDich.sotien);
            }

            if (mapKeys != null) mapKeys.clear();
            mapKeys = new ArrayList<String>(mapDay.keySet());
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction_expanded, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String dateStr = mapKeys.get(position);
            holder.data.clear();
            holder.data.addAll(mapDay.get(dateStr));

            holder.dateView.setText(dateStr);
            holder.adapter.notifyDataSetChanged();

            holder.moneyView.setText(PriceFormat.format(tongChiTieu.get(dateStr)));
        }

        @Override
        public int getItemCount() {
            return mapKeys.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
        {
            public TextView dateView;
            public TextView moneyView;
            public ImageButton expandButton;
            public RecyclerView listView;
            public ArrayList<ClassGiaoDich> data;
            public ListTransactionHomeAdapter adapter;

            public ViewHolder(View v)
            {
                super(v);
                bind(v);

                LinearLayoutManager manager = new LinearLayoutManager(activity_budget_info.this);
                adapter = new ListTransactionHomeAdapter(activity_budget_info.this, data);
                listView.setAdapter(adapter);
                listView.setLayoutManager(manager);
                listView.setVisibility(View.GONE);

                expandButton.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.expand_button:
                        if (listView.getVisibility() == View.GONE) {
                            listView.setVisibility(View.VISIBLE);
                            expandButton.setImageResource(R.drawable.ic_saving_withdraw);
                        } else {
                            listView.setVisibility(View.GONE);
                            expandButton.setImageResource(R.drawable.ic_saving_in);
                        }
                        break;
                }
            }

            void bind(View v) {
                dateView = v.findViewById(R.id.date);
                moneyView = v.findViewById(R.id.sumTotal);
                expandButton = v.findViewById(R.id.expand_button);
                listView = v.findViewById(R.id.recycler_view_by_day);
                data = new ArrayList<>();
            }
        }
    }
}
