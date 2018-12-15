package com.example.thang.smartmoney.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.PriceFormat;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class fragment_thongke extends Fragment {

    View view;
    Spinner timeSpinner;
    BarChart barChart;
    PieChart pieChartIncome;
    PieChart pieChartExpense;

    TextView textTotalIncome;
    TextView textTotalExpense;

    public fragment_thongke() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thongke, null);
        AnhXa();
        return view;
    }

    void AnhXa()
    {
        timeSpinner = view.findViewById(R.id.linear_chart_time);
        xulySpinner();

        barChart = view.findViewById(R.id.barchar);
        barChart.setNoDataText(getString(R.string.thongke_nodata));
        barChart.setFitBars(false);

        pieChartIncome = view.findViewById(R.id.piechar_income);
        pieChartIncome.setCenterText(getString(R.string.income_title));
        pieChartIncome.setCenterTextSize(16);
        pieChartIncome.setCenterTextColor(getResources().getColor(R.color.income));

        pieChartExpense = view.findViewById(R.id.piechar_expense);
        pieChartExpense.setCenterText(getString(R.string.expense_title));
        pieChartExpense.setCenterTextSize(16);
        pieChartExpense.setCenterTextColor(getResources().getColor(R.color.expense));

        textTotalIncome = view.findViewById(R.id.text_sum_income);
        textTotalExpense = view.findViewById(R.id.text_sum_expense);
    }

    void xulySpinner() {
        MonthAdapter adapter = new MonthAdapter(getActivity());
        timeSpinner.setAdapter(adapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("thongke", "Choose " + i);
                updateChart((int)l, 2018);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //default
        timeSpinner.setSelection(0);
    }


    void updateChart(int month, int year)
    {
        ArrayList<ClassGiaoDich> gdData;
        int totalIncome = 0;
        int totalExpense = 0;

        List<BarEntry> entriesIncome = new ArrayList<>();
        List<BarEntry> entriesExpense = new ArrayList<>();

        ContentValues cateIncome = new ContentValues();
        ContentValues cateExpense = new ContentValues();

        gdData = DBGiaoDich.getByMonth(month, year);
        int id = 0;
        for (ClassGiaoDich gd : gdData) {
            String cateName = ClassCategory.getName(gd.category_id);

            if (gd.from_id == 0) {
                entriesIncome.add(new BarEntry(id++, gd.sotien));
                totalIncome += gd.sotien;
                int oldVal = cateIncome.containsKey(cateName) ? cateIncome.getAsInteger(cateName) : 0;
                cateIncome.put(cateName, oldVal + gd.sotien);

            } else if (gd.to_id == 0) {
                entriesExpense.add(new BarEntry(id++, gd.sotien));
                totalExpense += gd.sotien;

                int oldVal = cateExpense.containsKey(cateName) ? cateExpense.getAsInteger(cateName) : 0;
                cateExpense.put(cateName, oldVal + gd.sotien);
            }
        }

        textTotalIncome.setText(PriceFormat.format(totalIncome));
        textTotalExpense.setText(PriceFormat.format(totalExpense));

        BarDataSet dataSetIncome = new BarDataSet(entriesIncome, getResources().getString(R.string.income_title));
        dataSetIncome.setColor(getResources().getColor(R.color.income));

        BarDataSet dataSetExpense = new BarDataSet(entriesExpense, getResources().getString(R.string.expense_title));
        dataSetExpense.setColor(getResources().getColor(R.color.expense));

        BarData barData = new BarData(dataSetExpense, dataSetIncome);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.groupBars(0, 1f, 0.02f);
        barChart.setHighlightFullBarEnabled(true);
        barChart.invalidate();

        updatePieChar(pieChartIncome, cateIncome);
        updatePieChar(pieChartExpense, cateExpense);
    }

    /**
     *
     * @param pieChart target to change
     * @param map the map values has stored
     */
    void updatePieChar(PieChart pieChart, ContentValues map)
    {
        List<PieEntry> entriesCategory = new ArrayList<>();
        for (String cate : map.keySet()) {
            entriesCategory.add(new PieEntry(map.getAsInteger(cate), cate));
        }

        PieDataSet pieDataSet = new PieDataSet(entriesCategory, getResources().getString(R.string.category_title));
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    private class MonthAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;

        public MonthAdapter(Context ctx) {
            layoutInflater = LayoutInflater.from(ctx);
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView text;

            if (view == null) {
                view = layoutInflater.inflate(android.R.layout.simple_spinner_item, null);
                text = view.findViewById(android.R.id.text1);
                view.setTag(text);
            } else {
                text = (TextView) view.getTag();
            }

            text.setText("Thang " + getItemId(i));
            return view;
        }

        @Override
        public long getItemId(int i) {
            return 12 - i;
        }
    }
}
