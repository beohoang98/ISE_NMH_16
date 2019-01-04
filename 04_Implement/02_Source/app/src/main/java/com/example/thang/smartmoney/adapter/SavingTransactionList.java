package com.example.thang.smartmoney.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.DBTietKiem;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.model.ClassVi;
import com.example.thang.smartmoney.xulysukien.DateFormat;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.ArrayList;
import java.util.List;

public class SavingTransactionList extends BaseAdapter {

    Activity activity;
    List<ClassTietKiem> tietKiemList;

    public SavingTransactionList(Activity activity)
    {
        this.activity = activity;
        DBTietKiem.init(activity);

        getData();
    }

    void getData() {
        tietKiemList = new ArrayList<>();
        List<ClassGiaoDich> giaoDichList = DBTietKiem.getGiaoDichBySoTietKiem(ClassVi.VI_TIET_KIEM);
        for (ClassGiaoDich giaoDich : giaoDichList) {
            tietKiemList.add(new ClassTietKiem(giaoDich));
        }
    }

    @Override
    public long getItemId(int position) {
        return tietKiemList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_saving_transaction, parent, false);

            holder = new ViewHolder();
            holder.inflate(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ClassTietKiem gd = (ClassTietKiem)getItem(position);
        if (gd.type == ClassTietKiem.TYPE.DEPOSIT) {
            holder.iconView.setImageDrawable(activity.getDrawable(R.drawable.ic_saving_in));
            holder.iconView.setBackgroundTintList(activity.getColorStateList(R.color.income));
            holder.sotienView.setTextColor(activity.getColor(R.color.income));
        } else {
            holder.iconView.setImageDrawable(activity.getDrawable(R.drawable.ic_saving_withdraw));
            holder.iconView.setBackgroundTintList(activity.getColorStateList(R.color.expense));
            holder.sotienView.setTextColor(activity.getColor(R.color.expense));
        }

        holder.sotienView.setText(PriceFormat.format(gd.sotien));
        holder.dateView.setText(DateFormat.format(gd.ngay));

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return tietKiemList.get(position);
    }

    @Override
    public int getCount() {
        return tietKiemList.size();
    }

    class ViewHolder {
        ImageView iconView;
        TextView sotienView, dateView;

        public void inflate(View view) {
            iconView = view.findViewById(R.id.icon);
            sotienView = view.findViewById(R.id.sotien);
            dateView = view.findViewById(R.id.date);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        getData();
    }
}
