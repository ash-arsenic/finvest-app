package com.example.finvest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PercentageFragment extends Fragment {

    private static TextInputEditText percentage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vg = inflater.inflate(R.layout.fragment_percentage, container, false);
        percentage = vg.findViewById(R.id.percentage_et);
        return vg;
    }

    public static String getPercentage() {
        return percentage.getText().toString();
    }
}