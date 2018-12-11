package com.example.thang.smartmoney.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.model.ClassCategory;

import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {

    Context mContext;
    ClassCategory.CATEGORY_TYPE type;
    LayoutInflater mLayoutInflater;
    List<ClassCategory> dataList;

    public CategorySpinnerAdapter(Context context, ClassCategory.CATEGORY_TYPE type)
    {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.type = type;
        dataList = ClassCategory.getByType(type);
    }

    public void setType(ClassCategory.CATEGORY_TYPE type) {
        this.type = type;
        dataList = ClassCategory.getByType(type);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryHolder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_category_spinner_item, null);
            holder = new CategoryHolder();

            holder.icon = convertView.findViewById(R.id.category_icon);
            holder.name = convertView.findViewById(R.id.category_name);

            convertView.setTag(holder);
        } else {
            holder = (CategoryHolder) convertView.getTag();
        }

        ClassCategory category = (ClassCategory) getItem(position);
        int iconId = mContext.getResources().getIdentifier(category.icon_url, "drawable", mContext.getPackageName());
        holder.icon.setImageResource(iconId);
        holder.name.setText(category.name);

        return convertView;
    }

    class CategoryHolder {
        ImageView icon;
        TextView name;
    }
}
