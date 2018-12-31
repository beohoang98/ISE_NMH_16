package com.example.thang.smartmoney.adapter;

import android.app.Activity;
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
import com.example.thang.smartmoney.model.ClassIcon;

import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {
    Activity activity;
    Context mContext;
    ClassCategory.CATEGORY_TYPE type;
    LayoutInflater mLayoutInflater;
    List<ClassCategory> dataList;

    public CategorySpinnerAdapter(Activity activity, ClassCategory.CATEGORY_TYPE type)
    {
        this.activity = activity;
        mContext = activity.getApplicationContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        ClassCategory.loadFromDB(mContext);
        this.type = type;
        dataList = ClassCategory.getByType(type);
    }

    public void setType(ClassCategory.CATEGORY_TYPE type) {
        this.type = type;
        dataList = ClassCategory.getByType(type);
    }

    public int getPositionOf(int category_id) {
        for (int i = 0; i < dataList.size(); ++i) {
            if (category_id == dataList.get(i).id) {
                return i;
            }
        }

        return -1;
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
        if (iconId > 0) {
            holder.icon.setImageResource(iconId);
        } else {
            // try local files
            boolean canSetIcon = ClassIcon.loadDownloadedIcon(category.icon_url + ".svg", holder.icon, activity);
            if (!canSetIcon) {
                // place holder with Android icon
                holder.icon.setImageResource(R.mipmap.ic_launcher_round);
            }
        }
        holder.name.setText(category.name);

        return convertView;
    }

    class CategoryHolder {
        ImageView icon;
        TextView name;
    }
}
