package com.example.finvest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RiskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RiskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RiskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RiskFragment newInstance(String param1, String param2) {
        RiskFragment fragment = new RiskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Button btn_unfocus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_risk, container, false);

        int[] btnId = {R.id.low_cap_risk, R.id.mid_cap_risk, R.id.large_cap_risk};
        Button[] btns = new Button[3];

        for(int i=0; i<3; i++) {
            btns[i] = view.findViewById(btnId[i]);
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.low_cap_risk:
                            setFocus(btn_unfocus, btns[0]);
                            break;

                        case R.id.mid_cap_risk:
                            setFocus(btn_unfocus, btns[1]);
                            break;

                        case R.id.large_cap_risk:
                            setFocus(btn_unfocus, btns[2]);
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