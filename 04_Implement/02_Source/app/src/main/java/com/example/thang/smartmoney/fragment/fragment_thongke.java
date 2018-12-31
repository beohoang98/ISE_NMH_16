package com.example.thang.smartmoney.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.PriceFormat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class fragment_thongke extends Fragment {

    View view;
    Spinner timeSpinner;
    LineChart lineChart;
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

    void AnhXa() {
        timeSpinner = view.findViewById(R.id.linear_chart_time);
        xulySpinner();

        Description barDes = new Description();
        barDes.setText(getString(R.string.thongke_month_description));

        lineChart = view.findViewById(R.id.linechar);
        lineChart.setNoDataText(getString(R.string.thongke_nodata));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDescription(barDes);
        lineChart.setDrawGridBackground(false);
        lineChart.setScaleMinima(1, 1);
        lineChart.getAxisLeft().setTextSize(7);
        lineChart.getXAxis().setValueFormatter(new LineCharAxisFormatter());

        Description pieDes = new Description();
        pieDes.setText(getString(R.string.thongke_piechart));

        pieChartIncome = view.findViewById(R.id.piechar_income);
        pieChartIncome.setCenterText(getString(R.string.income_title));
        pieChartIncome.setCenterTextSize(16);
        pieChartIncome.setCenterTextColor(getResources().getColor(R.color.income));
        pieChartIncome.setDescription(pieDes);

        pieChartExpense = view.findViewById(R.id.piechar_expense);
        pieChartExpense.setCenterText(getString(R.string.expense_title));
        pieChartExpense.setCenterTextSize(16);
        pieChartExpense.setCenterTextColor(getResources().getColor(R.color.expense));
        pieChartExpense.setDescription(pieDes);

        textTotalIncome = view.findViewById(R.id.text_sum_income);
        textTotalExpense = view.findViewById(R.id.text_sum_expense);
    }

    void xulySpinner() {
        final MonthAdapter adapter = new MonthAdapter(getActivity());
        timeSpinner.setAdapter(adapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("thongke", "Choose " + i);
                ((TextView)adapterView.getChildAt(0))
                        .setTextColor(getResources().getColor(android.R.color.white,null));

                Calendar cal = Calendar.getInstance();
                cal.setTime((Date)adapter.getItem(i));
                updateChart(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //default
        timeSpinner.setSelection(0);
    }


    void updateChart(int month, int year) {
        DataHolder holder = fetchData(month, year);

        textTotalIncome.setText(PriceFormat.format(holder.sumIncome));
        textTotalExpense.setText(PriceFormat.format(holder.sumExpense));

        LineDataSet dataSetExpense = getLineDataSetOf(holder.expenseEachDay,
                                getString(R.string.expense_title),
                                getResources().getColor(R.color.expense, null));
        LineDataSet dataSetIncome = getLineDataSetOf(holder.incomeEachDay,
                getString(R.string.income_title),
                getResources().getColor(R.color.income, null));

        LineData barData = new LineData(dataSetExpense, dataSetIncome);

        lineChart.setData(barData);
        lineChart.invalidate();

        updatePieChar(pieChartIncome, holder.categoryIncome);
        updatePieChar(pieChartExpense, holder.categoryExpense);
    }

    DataHolder fetchData(int month, int year) {
        DataHolder holder = new DataHolder();
        holder.gdData = DBGiaoDich.getByMonth(month, year);

        Calendar calStart = Calendar.getInstance();
        calStart.set(year, month, 0);
//        calStart.add(Calendar.DAY_OF_MONTH, -1); // end of month

        Calendar calEnd = (Calendar)calStart.clone();
        calEnd.add(Calendar.MONTH, -1);
        calEnd.add(Calendar.DAY_OF_YEAR, 1);


        while (calStart.getTime().after(calEnd.getTime())) {
            String day = "" + calStart.get(Calendar.DAY_OF_MONTH);
            calStart.add(Calendar.DAY_OF_YEAR, -1);
            holder.incomeEachDay.put(day, 0);
            holder.expenseEachDay.put(day, 0);
        }

        for (ClassGiaoDich gd : holder.gdData) {
            String cateName = ClassCategory.getName(gd.category_id);
            String day = gd.thoigian.substring(0, 2);

            if (gd.from_id == 0) {
                int oldVal = holder.categoryIncome.containsKey(cateName) ? holder.categoryIncome.getAsInteger(cateName) : 0;
                int oldSum = (holder.incomeEachDay.containsKey(day)) ? holder.incomeEachDay.getAsInteger(day) : 0;

                holder.incomeEachDay.put(day, oldSum + gd.sotien);
                holder.sumIncome += gd.sotien;
                holder.categoryIncome.put(cateName, oldVal + gd.sotien);

            } else if (gd.to_id == 0) {
                int oldVal = holder.categoryExpense.containsKey(cateName) ? holder.categoryExpense.getAsInteger(cateName) : 0;
                int oldSum = (holder.expenseEachDay.containsKey(day)) ? holder.expenseEachDay.getAsInteger(day) : 0;

                holder.expenseEachDay.put(day, oldSum + gd.sotien);
                holder.sumExpense += gd.sotien;
                holder.categoryExpense.put(cateName, oldVal + gd.sotien);
            }
        }

        return holder;
    }

    LineDataSet getLineDataSetOf(ContentValues map, String title, int color) {
        List<Entry> entries = new ArrayList<>();
        for (String day : map.keySet())
        {
            int id = Integer.parseInt(day);
            int sum = map.getAsInteger(day);
            if (sum > 0) entries.add(new Entry(id, sum));
        }

        LineDataSet dataSet = new LineDataSet(entries, title);
        dataSet.setColor(color);
        dataSet.setValueFormatter(new PriceFormatter());
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setValueTextSize(7);
        dataSet.setLineWidth(1.5f);

        return dataSet;
    }

    /**
     *
     * @param pieChart target to change
     * @param map the map values has stored
     */
    void updatePieChar(PieChart pieChart, ContentValues map) {
        List<PieEntry> entriesCategory = new ArrayList<>();
        for (String cate : map.keySet()) {
            entriesCategory.add(new PieEntry(map.getAsInteger(cate), cate));
        }

        PieDataSet pieDataSet = new PieDataSet(entriesCategory, getResources().getString(R.string.category_title));
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(8);
        pieChart.setEntryLabelColor(getResources().getColor(android.R.color.black));
        pieChart.invalidate();
    }


    class MonthAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        Calendar cal;
        SimpleDateFormat dateFormat;

        public MonthAdapter(Context ctx) {
            layoutInflater = LayoutInflater.from(ctx);
            cal = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);
        }

        @Override
        public Object getItem(int i) {
            Calendar otherCal = (Calendar) cal.clone();
            otherCal.add(Calendar.MONTH, -i);
            return otherCal.getTime();
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

            String time = dateFormat.format((Date)getItem(i));
            text.setText(time);
            return view;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }

    class PriceFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value == 0) return "";
            return PriceFormat.format((int)value) + " " + getString(R.string.price_label);
        }
    }
    class LineCharAxisFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "" + (int)value;
        }
    }
    class DataHolder {
        public ArrayList<ClassGiaoDich> gdData;
        public ContentValues incomeEachDay;
        public ContentValues expenseEachDay;
        public ContentValues categoryIncome;
        public ContentValues categoryExpense;
        public int sumIncome = 0;
        public int sumExpense = 0;
        public DataHolder() {
            incomeEachDay = new ContentValues();
            expenseEachDay = new ContentValues();
            categoryIncome = new ContentValues();
            categoryExpense = new ContentValues();
        }
    }
}
