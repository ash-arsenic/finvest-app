package com.example.finvest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import maes.tech.intentanim.CustomIntent;

public class FundDetailsActivity extends AppCompatActivity {
    FundsModal fund;

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(FundDetailsActivity.this, "right-to-left");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_details);

        String json = getIntent().getStringExtra("FUND");
        Gson gson = new Gson();
        Type type = new TypeToken<FundsModal>() {}.getType();
        fund = gson.fromJson(json, type);

        TextView fundName = findViewById(R.id.fund_name);
        TextView graphDetails = findViewById(R.id.fund_graph_explanation);
        ImageView fundGraph = findViewById(R.id.fund_graph);
        Button buyFunds = findViewById(R.id.buy_funds);

        fundName.setText(fund.getName());
        String details = "Below mentioned graph shows the ups and highs of the mutual fund over the last six months from Jan 2021 to Jul 2021.\n" +
                "The horizontal axis shows the time in months and the vertical axis shows the net asset value.\n";
        graphDetails.setText(details+details);

        buyFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hdfcfund.com/"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}