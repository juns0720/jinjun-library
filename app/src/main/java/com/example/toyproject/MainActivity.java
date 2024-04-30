package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<CharSequence> adUniv, adMaj, adGrd;
    private Spinner univSpin, majSpin, grdSpin;
    private ImageButton imgBtnLogo;
    private CategorySpinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        univSpin = findViewById(R.id.university);
        majSpin = findViewById(R.id.major);
        grdSpin = findViewById(R.id.grade);
        imgBtnLogo = findViewById(R.id.logoImageButton);

        adUniv = ArrayAdapter.createFromResource(this, R.array.spinner_univercity, android.R.layout.simple_spinner_dropdown_item);
        adUniv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(adUniv);

        categorySpinner = new CategorySpinner(this, adUniv, univSpin, majSpin, grdSpin);
        categorySpinner.setSpinner();

        //로고 클릭시 초기값 지정//
        imgBtnLogo.setOnClickListener(v -> univSpin.setSelection(0));


    }






}