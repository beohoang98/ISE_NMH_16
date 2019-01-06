package com.example.thang.smartmoney;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thang.smartmoney.fragment.CategoryListFragment;
import com.example.thang.smartmoney.adapter.IconAdapter;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassIcon;

import java.util.ArrayList;
import java.util.List;

public class category_activity extends AppCompatActivity
{
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;

    Dialog addCateDialog;
    Spinner dialogSpinner;
    CategoryFragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        AnhXa();
    }

    void AnhXa()
    {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addCateDialog = new Dialog(this);
        addCateDialog.setContentView(R.layout.dialog_add_category);
        dialogSpinner = addCateDialog.findViewById(R.id.iconInput);
        IconAdapter iconAdapter = new IconAdapter(category_activity.this);
        dialogSpinner.setAdapter(iconAdapter);


        adapter = new CategoryFragmentAdapter(getSupportFragmentManager());

        CategoryListFragment cateIncome = new CategoryListFragment();
        cateIncome.setType(ClassCategory.CATEGORY_TYPE.INCOME);
        CategoryListFragment cateExpense = new CategoryListFragment();
        cateExpense.setType(ClassCategory.CATEGORY_TYPE.EXPENSE);
        adapter.addFrag(cateIncome, getResources().getString(R.string.income_title));
        adapter.addFrag(cateExpense, getResources().getString(R.string.expense_title));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_category:
                addCateDialog.show();

                break;
        }

        return true;
    }



    public void onCategoryDialogAddRequest(View view) {
        String addName = ((EditText)addCateDialog.findViewById(R.id.nameInput)).getText().toString();
        ClassCategory.CATEGORY_TYPE addType = null;
        long iconId = dialogSpinner.getSelectedItemId();

        RadioGroup typeRadio = addCateDialog.findViewById(R.id.radio_group);
        switch (typeRadio.getCheckedRadioButtonId())
        {
            case R.id.type_income:
                addType = ClassCategory.CATEGORY_TYPE.INCOME;
                break;
            case R.id.type_expense:
                addType = ClassCategory.CATEGORY_TYPE.EXPENSE;
                break;
        }

        if (addName.equals("")) {
            Toast.makeText(this, R.string.category_null, Toast.LENGTH_LONG).show();
            addCateDialog.dismiss();
            return;
        }

        if (addType == null) {
            Toast.makeText(this, R.string.category_null, Toast.LENGTH_LONG).show();
            addCateDialog.dismiss();
            return;
        }

        long rows = ClassCategory.add(addName, addType, ClassIcon.list.get((int)iconId));
        if (rows > 0) {
            String icon_name = ClassIcon.list.get((int)iconId);
            String icon_url = ClassIcon.getUrlAt((int)iconId);
            new WaitIconAdded().execute(icon_name, icon_url);
            ClassCategory.loadFromDB(getBaseContext());
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Loi", Toast.LENGTH_LONG);
            addCateDialog.dismiss();
        }
    }

    class CategoryFragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;
        private Context ctx;

        public CategoryFragmentAdapter(FragmentManager fm)
        {
            super(fm);
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }

        public void addFrag(Fragment frag, String title) {
            fragments.add(frag);
            titles.add(title);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return fragments.indexOf(object);
        }
    }

    class WaitIconAdded extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String name = strings[0];
            String url = strings[1];
            ClassIcon.downloadIcon(name, url);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            Toast.makeText(category_activity.this, "Add thanh cong", Toast.LENGTH_LONG);
            addCateDialog.dismiss();
        }
    }
}
