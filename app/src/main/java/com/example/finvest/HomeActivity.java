package com.example.finvest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    BarChart barChart;
    BarData barData;
    List<BarEntry> barEntryList = new ArrayList<>();
    List<Integer> barColors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(40,"SBI"));
        pieEntryList.add(new PieEntry(15,"ICICI"));
        pieEntryList.add(new PieEntry(25,"PNB"));
        pieEntryList.add(new PieEntry(20,"Yes"));
        colors.add(Color.rgb(46, 255, 205));
        colors.add(Color.rgb(117, 95, 255));
        colors.add(Color.rgb(206, 255, 66));
        colors.add(Color.rgb(255, 95, 95));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
        pieDataSet.setColors(colors);
        pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(16f);
        pieData.setValueTextColor(Color.rgb(255,255,255));
        pieChart.setData(pieData);
        pieChart.setHoleRadius(30);
        pieChart.animateX(2000);
        pieChart.setDrawSliceText(false);
        pieChart.setTransparentCircleRadius(40);
        pieChart.invalidate();

        ArrayList<String> risks = new ArrayList<>();
        risks.add("Large");
        risks.add("Medium");
        risks.add("Small");
        barChart = findViewById(R.id.barchart);
        barEntryList.add(new BarEntry(0, 50));
        barEntryList.add(new BarEntry(1, 30));
        barEntryList.add(new BarEntry(2, 20));
        BarDataSet bardataset = new BarDataSet(barEntryList, "Distribution");
        BarData data = new BarData(bardataset);
        data.setValueTextSize(16f);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
        barChart.animateY(5000);

        XAxis xAxis = barChart.getXAxis();
//        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);
        yAxis.setDrawAxisLine(false);

        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(risks));
        barChart.invalidate();
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String out = "";
                if(e.getX() == 0) {
                    out = "Large Cap";
                }else if(e.getX() == 1) {
                    out = "Medium Cap";
                } else {
                    out = "Small Cap";
                }
                Toast.makeText(HomeActivity.this, out, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
}