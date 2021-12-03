package com.example.finvest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProgressActivity extends AppCompatActivity {

    String[] smallCap;
    String[] largeCap;
    String[] debt;
    String amountInvested, amountReturned;
    boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
/////////////USER DATA//////////////////////////////////
        int amount = Math.round(Float.parseFloat(PercentageFragment.getPercentage())*Float.parseFloat(SalaryFragment.getSalary())/100);
        int time = Integer.parseInt(TimeFragment.getTime());
        int selectedRisk = RiskFragment.selectedBtn;
        int risk = 20;
        if(selectedRisk == 1) {
            risk = 60;
        } else if(selectedRisk == 2) {
            risk = 90;
        }

        ///////////////LOAD DATA/////////////////////////

        String url = "https://finvest-app.herokuapp.com/give";
//        String url = "http://192.168.0.105:5000/give";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("ch", "IDCW")
                .add("amo", String.valueOf(amount))
                .add("ti", String.valueOf(time))
                .add("ri", String.valueOf(risk))
                .build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProgressActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();


                str = str.substring(1, str.length()-1);
                String[] risks = str.split("\\}, ");

                risks[0] = risks[0].substring(21);
                smallCap = risks[0].split(", ");
                smallCap[0] = smallCap[0].substring(1, smallCap[0].length()-1);
                smallCap[1] = smallCap[1].substring(11);

                risks[1] = risks[1].substring(21);
                largeCap = risks[1].split(", ");
                largeCap[0] = largeCap[0].substring(1, largeCap[0].length()-1);
                largeCap[1] = largeCap[1].substring(11);

                risks[2] = risks[2].substring(17);
                debt = risks[2].split(", ");
                debt[0] = debt[0].substring(1, debt[0].length()-1);
                debt[1] = debt[1].substring(11);

                String amounts[] = risks[3].split(", ");
                amountInvested = amounts[0].substring(18);
                amountReturned = amounts[1].substring(10);

                Float ret = Float.parseFloat(amountReturned);
                int ter = Math.round(ret);
                amountReturned = String.valueOf(ter);
//                String logOut = smallCap[0] + " " + smallCap[1] + "\n" +
//                        largeCap[0] + " " + largeCap[1] + "\n" +
//                        debt[0] + " " + debt[1] + "\n" +
//                        "Invested: " + amountInvested + "\n" +
//                        "Returned: " + amountReturned + "\n";
//                Log.d("MSG", logOut);
                start = true;
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(start) {
                    Intent intent = new Intent(ProgressActivity.this, HomeActivity.class);
                    intent.putExtra("SCN", smallCap[0]);
                    intent.putExtra("SCV", smallCap[1]);
                    intent.putExtra("LCN", largeCap[0]);
                    intent.putExtra("LCV", largeCap[1]);
                    intent.putExtra("DN", debt[0]);
                    intent.putExtra("DV", debt[1]);

                    intent.putExtra("AI", amountInvested);
                    intent.putExtra("AR", amountReturned);


                    startActivity(intent);
                    finish();
                }
            }
        }, 5000);
    }
}