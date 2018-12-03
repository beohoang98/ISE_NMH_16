package com.example.thang.smartmoney;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterAdd extends BaseAdapter {

    private Activity context;
    private int layout;
    private List<AddGhiChu> ghiChuList;

    public AdapterAdd(Activity context, int layout, List<AddGhiChu> ghiChuList) {
        this.context = context;
        this.layout = layout;
        this.ghiChuList = ghiChuList;
    }

    @Override
    public int getCount() {
        return ghiChuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class   ViewHolder{
        TextView txtTen;
        TextView txtdate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtTen=convertView.findViewById(R.id.txtten);
            holder.txtdate=convertView.findViewById(R.id.txtdate);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        AddGhiChu ghiChu=ghiChuList.get(position);
        holder.txtTen.setText(ghiChu.getNameGC());
        holder.txtdate.setText(ghiChu.getDateGC());
        return convertView;
    }
}
