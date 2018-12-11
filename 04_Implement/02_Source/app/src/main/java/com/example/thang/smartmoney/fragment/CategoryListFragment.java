package com.example.thang.smartmoney.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.CategorySpinnerAdapter;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassIcon;

public class CategoryListFragment extends Fragment {

    private ClassCategory.CATEGORY_TYPE type = ClassCategory.CATEGORY_TYPE.EXPENSE;
    private ListView listView;
    private CategorySpinnerAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_list, null);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        ClassCategory.loadFromDB(getContext());

        listView = view.findViewById(R.id.category_listView);
        adapter = new CategorySpinnerAdapter(getContext(), this.type);
        listView.setAdapter(adapter);
    }

    public void setType(ClassCategory.CATEGORY_TYPE type) {
        this.type = type;
//        adapter.setType(type);
//        adapter.notifyDataSetChanged();
    }
}
