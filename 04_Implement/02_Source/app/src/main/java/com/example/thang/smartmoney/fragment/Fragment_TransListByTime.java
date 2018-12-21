package com.example.thang.smartmoney.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.adapter.ListTransactionHomeAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.edit_giaodich;
import com.example.thang.smartmoney.home_activity;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.DateFormat;
import com.example.thang.smartmoney.xulysukien.PriceFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Fragment_TransListByTime extends Fragment
    implements ListTransactionHomeAdapter.OnClickListener
{
    private View view;
    private RecyclerView listView;
    private TextView titleView;

    private TextView sumIncomeText;
    private TextView sumOutcomeText;
    private TextView sumTotalText;

    private ArrayList<ClassGiaoDich> listTransactions;
    private ListTransactionHomeAdapter adapter;
    private String dateStr;
    private Date date;

    public void setTime(Date date) {
        this.dateStr = DateFormat.format(date);
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaction_list_by_time, container, false);
        DBVi.init(view.getContext());

        AnhXa();

        showData();
        return view;
    }

    void AnhXa() {
        titleView = view.findViewById(R.id.frag_trans_list_date_title);
        listView = view.findViewById(R.id.list_item);
        sumIncomeText = view.findViewById(R.id.sumIncome);
        sumOutcomeText = view.findViewById(R.id.sumOutcome);
        sumTotalText = view.findViewById(R.id.sumTotal);

        listTransactions = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ListTransactionHomeAdapter(getActivity(), listTransactions);
        adapter.setOnItemSelected(this);

        listView.setAdapter(adapter);
        listView.setLayoutManager(layoutManager);

        ItemTouchHelper helper = new ItemTouchHelper(new mTouchCallback());
        helper.attachToRecyclerView(listView);


        Calendar thisDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        thisDate.setTime(date);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        Calendar yesterday = (Calendar)now.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        if (thisDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            this.dateStr = getContext().getString(R.string.date_today);
        } else if (thisDate.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            this.dateStr = getContext().getString(R.string.date_yesterday);
        }
    }

    void showData() {
        int tongIncome = DBVi.getTongIncomeByDate(date);
        int tongOutcome = DBVi.getTongOutcomeByDate(date);
        int tong = tongIncome - tongOutcome;

        titleView.setText(dateStr);
        sumIncomeText.setText("+ " + PriceFormat.format(tongIncome));
        sumOutcomeText.setText("- " + PriceFormat.format(tongOutcome));
        sumTotalText.setText(PriceFormat.format(tong));
        if (tong >= 0) {
            sumTotalText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.income));
        } else {
            sumTotalText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.expense));
        }

        listTransactions.clear();
        listTransactions.addAll(0, DBGiaoDich.getByDate(date));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view, int position) {
        int id = listTransactions.get(position).id;

        Intent editIntent = new Intent(getActivity(), edit_giaodich.class);
        editIntent.putExtra("id", id);
        getActivity().startActivityForResult(editIntent, home_activity.EDIT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    class mTouchCallback extends ItemTouchHelper.SimpleCallback {
        Drawable icon;
        Drawable background;

        public mTouchCallback() {
            super(0, ItemTouchHelper.LEFT);
            icon = getContext().getDrawable(R.drawable.ic_delete);
            icon.setTint(Color.WHITE);
            background = new ColorDrawable(Color.RED);
        }


        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            final Dialog deleteDialog = new Dialog(getContext());
            deleteDialog.setContentView(R.layout.dialog_add_remove);
            Button okBtn = deleteDialog.findViewById(R.id.btnXoadgl);
            Button cancelBtn = deleteDialog.findViewById(R.id.btnHUYdgl);

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = adapter.getItemId(position);
                    if (DBGiaoDich.xoa((int)id)) {
                        Toast.makeText(getContext(), "Xoa giao dich thang cong", Toast.LENGTH_SHORT).show();
                        listTransactions.remove(position);
                        adapter.notifyItemRemoved(position);
                    } else {
                        Toast.makeText(getContext(), "Xoa giao dich loi", Toast.LENGTH_SHORT).show();
                    }
                    deleteDialog.dismiss();
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteDialog.cancel();
                }
            });
            deleteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    adapter.notifyItemChanged(position);
                }
            });

            deleteDialog.show();
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (dX < 0) {
                View view = viewHolder.itemView;
                int margin = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
                int opacity = (dX < -200) ? 255 : Math.round(-dX * 255 / 200);

                int iconLeft = view.getRight() - icon.getIntrinsicWidth()*2;
                int iconRight = view.getRight() - icon.getIntrinsicWidth();
                int iconTop = view.getTop() + margin;
                int iconBottom = view.getBottom() - margin;

                background.setBounds(
                        Math.round(view.getRight() + dX),
                        view.getTop() + view.getPaddingTop(),
                        view.getRight() - view.getPaddingRight(),
                        view.getBottom() - view.getPaddingBottom()
                );
                background.draw(c);

                icon.setAlpha(opacity);
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                icon.draw(c);
            }

        }
    }
}
