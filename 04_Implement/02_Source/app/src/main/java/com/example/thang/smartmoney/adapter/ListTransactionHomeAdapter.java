package com.example.thang.smartmoney.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassIcon;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.model.ClassVi;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.ArrayList;

public class ListTransactionHomeAdapter extends RecyclerView.Adapter<ListTransactionHomeAdapter.ViewHolder> {
    private ArrayList<ClassGiaoDich> list;
    private Context context;
    private Activity activity;
    private OnClickListener onClickListener;

    public ListTransactionHomeAdapter(Activity act, ArrayList<ClassGiaoDich> ghiChuList) {
        this.list = ghiChuList;
        activity = act;
        this.context = activity.getBaseContext();
        ClassCategory.loadFromDB(activity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).id;
    }

    public void setOnItemSelected(OnClickListener onItemSelected) {
        this.onClickListener = onItemSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_transaction_item, parent, false);

        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassGiaoDich addGhiChu = this.list.get(position);
        ClassCategory category = ClassCategory.getById(addGhiChu.category_id);
        if (category != null) {
            int iconId = context.getResources().getIdentifier(category.icon_url, "drawable", context.getPackageName());
            if (iconId > 0)
                holder.icon.setImageResource(iconId);
            else if (!ClassIcon.loadDownloadedIcon(category.icon_url + ".svg", holder.icon, activity)) {
                holder.icon.setImageResource(R.drawable.lo_go);
            }
            holder.category.setText(ClassCategory.getName(addGhiChu.category_id));
        }

        holder.note.setText(addGhiChu.note);
        holder.price.setText( PriceFormat.format(addGhiChu.sotien) );

        if (addGhiChu.to_id == ClassVi.VI_CHINH_ID) {
            // income
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.income));
        } else {
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.expense));
            holder.price.setText("-" + holder.price.getText());
        }

        if (ClassTietKiem.isTietKiem(addGhiChu)) {
            holder.icon.setImageResource(R.drawable.ic_saving);
            holder.category.setText(R.string.saving_title);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {

        ImageView icon;
        TextView category;
        TextView price;
        TextView note;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            icon = view.findViewById(R.id.transaction_list_icon);
            category = view.findViewById(R.id.transaction_list_category);
            price = view.findViewById(R.id.transaction_list_price);
            note = view.findViewById(R.id.transaction_list_note);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view, getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }
}
