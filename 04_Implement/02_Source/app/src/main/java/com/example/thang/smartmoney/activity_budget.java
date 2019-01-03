package com.example.thang.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.thang.smartmoney.adapter.BudgetListAdapter;
import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.model.ClassNganSach;

import java.util.List;

public class activity_budget extends AppCompatActivity
    implements BudgetListAdapter.OnSelectListener
{

    Toolbar toolbar;
    RecyclerView recyclerView;

    BudgetListAdapter adapter;

    List<ClassNganSach> nganSachList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        AnhXa();
    }

    void AnhXa()
    {
        nganSachList = DBNganSach.getInstance(this).getAllNganSach();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BudgetListAdapter(this, nganSachList);
        recyclerView.setAdapter(adapter);

        adapter.setOnSelectListener(this);
    }

    @Override
    public void onClick(View v, int position, int id) {
        Intent intent = new Intent(this, activity_budget_add.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_budget_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nganSachList = DBNganSach.getInstance(this).getAllNganSach();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.add:
                startActivity(new Intent(this, activity_budget_add.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
