package com.example.finvest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RiskFragment extends Fragment {

    Button btn_unfocus;
    public static int selectedBtn = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_risk, container, false);

        int[] btnId = {R.id.low_cap_risk, R.id.mid_cap_risk, R.id.large_cap_risk};
        Button[] btns = new Button[3];

        selectedBtn = 0;
        for(int i=0; i<3; i++) {
            btns[i] = view.findViewById(btnId[i]);
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.low_cap_risk:
                            setFocus(btn_unfocus, btns[0]);
                            selectedBtn = 0;
                            break;

                        case R.id.mid_cap_risk:
                            setFocus(btn_unfocus, btns[1]);
                            selectedBtn = 1;
                            break;

                        case R.id.large_cap_risk:
                            setFocus(btn_unfocus, btns[2]);
                            selectedBtn = 2;
                            break;
                    }
                }
            });
        }
        btn_unfocus = btns[0];

        return view;
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(77, 191, 132));
        this.btn_unfocus = btn_focus;
    }
}