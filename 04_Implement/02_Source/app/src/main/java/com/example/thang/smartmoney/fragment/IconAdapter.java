package com.example.thang.smartmoney.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.model.ClassIcon;
import com.squareup.picasso.Picasso;

public class IconAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public IconAdapter(Context ctx)
    {
        context = ctx;
        layoutInflater = LayoutInflater.from(context);
        ClassIcon.load(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_choose_icon, null);
            holder = new ViewHolder();

            holder.icon = convertView.findViewById(R.id.img_icon);
            holder.name = convertView.findViewById(R.id.icon_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String url = (String)getItem(position);
        Picasso.get().load(url).into(holder.icon);
        holder.name.setText(ClassIcon.list.get(position));

        return convertView;
    }

//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        return getView(position, convertView, parent);
//    }

    @Override
    public int getCount() {
        return ClassIcon.list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return ClassIcon.getUrlAt(position);
    }

    class ViewHolder {
        AppCompatImageView icon;
        TextView name;
    }
}


