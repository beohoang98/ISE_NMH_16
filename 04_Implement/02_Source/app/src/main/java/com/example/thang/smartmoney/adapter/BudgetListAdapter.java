package com.example.thang.smartmoney.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.xulysukien.DateFormat;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.List;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.ViewHolder> {

    Activity activity;
    Context context;
    List<ClassNganSach> data;

    OnSelectListener onSelectListener;

    public BudgetListAdapter(Activity activity, List<ClassNganSach> data)
    {
        this.activity = activity;
        this.context = activity.getBaseContext();
        this.data = data;
        onSelectListener = null;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ClassNganSach nganSach = data.get(position);
        final int _position = position; 
        if (nganSach == null) {
            holder.name.setText("Chu co data");
            return;
        }

        String range = String.format("%s - %s",
                DateFormat.format(nganSach.getNgayBD()),
                DateFormat.format(nganSach.getNgayKT()));

        int tongChi = DBNganSach.getInstance(context).getTongChi(nganSach.getId(), null);

        holder.name.setText(nganSach.getName());
        holder.range_date.setText(range);
        holder.total.setText(PriceFormat.format(nganSach.getSoTien()));
        holder.totalLeft.setText(PriceFormat.format(nganSach.getSoTien() - tongChi));

        holder.progressBar.setMax(nganSach.getSoTien());
        holder.progressBar.setProgress(tongChi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClick(v, _position, nganSach.getId());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_list_budget, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView range_date;
        public TextView total;
        public TextView totalLeft;
        public ProgressBar progressBar;

        public ViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.name);
            range_date = view.findViewById(R.id.range_date);
            total = view.findViewById(R.id.total);
            totalLeft = view.findViewById(R.id.total_left);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    public interface OnSelectListener
    {
        void onClick(View v, int position, int id);
    }
}
