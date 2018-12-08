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
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

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
        holder.price.setText( PriceFormat.format(addGhiChu.sotien) );
        holder.category.setText(ClassCategory.getName(addGhiChu.category_id));

        if (addGhiChu.from_id == 0) {
            // income
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.income));
        } else {
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.expense));
            holder.price.setText("-" + holder.price.getText());
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView category;
        TextView price;
        TextView note;
    }
}
