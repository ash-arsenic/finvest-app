package com.example.finvest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    BarChart barChart;
    BarData barData;
    List<BarEntry> barEntryList = new ArrayList<>();
    List<Integer> barColors = new ArrayList<>();

    String[] smallCap;
    String[] largeCap;
    String[] debt;
    String amountInvested, amountReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toast.makeText(HomeActivity.this, "Selected: "+RiskFragment.selectedBtn, Toast.LENGTH_SHORT).show();

        ////////////////////DEFAULTS///////////////////////
        smallCap = new String[2];
        smallCap[0] = getIntent().getStringExtra("SCN");
        smallCap[1] = getIntent().getStringExtra("SCV");

        largeCap = new String[2];
        largeCap[0] = getIntent().getStringExtra("LCN");
        largeCap[1] = getIntent().getStringExtra("LCV");

        debt = new String[2];
        debt[0] = getIntent().getStringExtra("DN");
        debt[1] = getIntent().getStringExtra("DV");

        amountInvested = getIntent().getStringExtra("AI");
        amountReturned = getIntent().getStringExtra("AR");

        /////////////////SET GRAPH/////////////////////////
        TextView tInv = findViewById(R.id.total_investment);
        tInv.setText(amountInvested + "₹");

        TextView tRet = findViewById(R.id.total_return);
        tRet.setText(amountReturned + "₹");

        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(Float.parseFloat(smallCap[1]),smallCap[0]));
        pieEntryList.add(new PieEntry(Float.parseFloat(largeCap[1]),largeCap[0]));
        pieEntryList.add(new PieEntry(Float.parseFloat(debt[1]),debt[0]));
        colors.add(Color.rgb(46, 255, 205));
        colors.add(Color.rgb(117, 95, 255));
        colors.add(Color.rgb(255, 95, 95));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
        pieDataSet.setColors(colors);
        pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(16f);
        pieData.setValueTextColor(Color.rgb(255,255,255));
        pieChart.setData(pieData);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setYOffset(5f);
        l.setEnabled(false);

        pieChart.setHoleRadius(30);
        pieChart.animateX(2000);
        pieChart.setDrawSliceText(false);
        pieChart.setTransparentCircleRadius(40);
        pieChart.invalidate();

        int smallAmount = (int) (Float.parseFloat(smallCap[1]) * Float.parseFloat(amountInvested) / 100);

        int largeAmount = (int) (Float.parseFloat(largeCap[1]) * Float.parseFloat(amountInvested) / 100);

        int debtAmount = (int) (Float.parseFloat(debt[1]) * Float.parseFloat(amountInvested) / 100);

        ArrayList<String> risks = new ArrayList<>();
        risks.add("Small Cap");
        risks.add("Large Cap");
        risks.add("Debt");
        ArrayList<Integer> barColors = new ArrayList<>();
        barColors.add(Color.rgb(46, 255, 205));
        barColors.add(Color.rgb(117, 95, 255));
        barColors.add(Color.rgb(206, 255, 66));
        barChart = findViewById(R.id.barchart);
        barEntryList.add(new BarEntry(0, smallAmount));
        barEntryList.add(new BarEntry(1, largeAmount));
        barEntryList.add(new BarEntry(2, debtAmount));
        BarDataSet bardataset = new BarDataSet(barEntryList, "Distribution");
        BarData data = new BarData(bardataset);
        data.setValueTextSize(16f);
        bardataset.setColors(colors);
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
                    out = "Small Cap";
                }else if(e.getX() == 1) {
                    out = "Large Cap";
                } else {
                    out = "Debt";
                }
//                        Toast.makeText(HomeActivity.this, out, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        ////////////////////////GRAPH END//////////////////////

        ArrayList<FundsModal> funds = new ArrayList<>();
        funds.add(new FundsModal(smallCap[0], "", colors.get(0)));
        funds.add(new FundsModal(largeCap[0], "", colors.get(1)));
        funds.add(new FundsModal(debt[0], "", colors.get(2)));

        RecyclerView fundDetailsRV = findViewById(R.id.funds_details_rv);
        fundDetailsRV.setHasFixedSize(true);
        fundDetailsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fundDetailsRV.setAdapter(new FundDetailsAdapter(funds));

    }

    private class FundDetailsAdapter extends RecyclerView.Adapter<FundsDetailsViewHolder> {

        ArrayList<FundsModal> funds;

        public FundDetailsAdapter(ArrayList<FundsModal> funds) {
            this.funds = funds;
        }

        @NonNull
        @Override
        public FundsDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.funds_details_row, parent, false);
            return new FundsDetailsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FundsDetailsViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.fundName.setText(funds.get(position).getName());
            holder.fundColor.setBackgroundColor(funds.get(position).getColor());
            holder.fundDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, FundDetailsActivity.class);
                    intent.putExtra("FUND", (new Gson()).toJson(funds.get(position)));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return funds.size();
        }
    }

    private class FundsDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView fundName;
        Button fundDetails;
        ImageButton fundColor;
        public FundsDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            fundName = itemView.findViewById(R.id.funds_details_name);
            fundDetails = itemView.findViewById(R.id.funds_details_btn);
            fundColor = itemView.findViewById(R.id.funds_details_color);
        }
    }
}