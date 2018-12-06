package com.example.thang.smartmoney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassGiaoDich;

import java.util.ArrayList;

public class ListTransactionHomeAdapter extends BaseAdapter {
    private ArrayList<ClassGiaoDich> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListTransactionHomeAdapter(Context context, ArrayList<ClassGiaoDich> ghiChuList) {
        this.list = ghiChuList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_transaction_item, null);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.transaction_list_icon);
            holder.category = convertView.findViewById(R.id.transaction_list_category);
            holder.price = convertView.findViewById(R.id.transaction_list_price);
            holder.note = convertView.findViewById(R.id.transaction_list_note);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ClassGiaoDich addGhiChu = this.list.get(position);

        holder.icon.setImageResource(R.drawable.lo_go);
        holder.note.setText(addGhiChu.note);
        holder.price.setText(addGhiChu.sotien);
        holder.category.setText(ClassCategory.getName(addGhiChu.category_id));

        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView category;
        TextView price;
        TextView note;
    }
}
