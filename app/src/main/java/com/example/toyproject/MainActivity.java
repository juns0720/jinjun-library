package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<CharSequence> adUniv, adMaj, adGrd;
    private Spinner univSpin, majSpin, grdSpin;
//    private String choice_univ = "";
//    private String choice_maj = "";
//    private String choice_grd = "";
    private ImageButton imgBtnLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        univSpin = findViewById(R.id.university);
        majSpin = findViewById(R.id.major);
        grdSpin = findViewById(R.id.grade);
        imgBtnLogo = findViewById(R.id.logoImageButton);
        adUniv = ArrayAdapter.createFromResource(this, R.array.spinner_univercity, android.R.layout.simple_spinner_dropdown_item);
        adUniv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(adUniv);

        //로고 클릭시 초기값 지정//
        imgBtnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                univSpin.setSelection(0);
            }
        });

        //학교 선택시 학과 어댑터 저장
        univSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(adUniv.getItem(position).equals("성결대학교")){
//                    choice_univ = "성결대학교";
                    initSpinner(majSpin, adMaj,R.array.spinner_univercity_sungkyul);
                    majSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (adUniv.getItem(position).equals("서울대학교")) {
//                    choice_univ = "서울대학교";
                    initSpinner(majSpin, adMaj, R.array.spinner_univercity_seoul);

                    majSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (adUniv.getItem(position).equals("메사추세츠 공과대학교")) {
//                    choice_univ = "메사추세츠 공과대학교";
                    initSpinner(majSpin, adMaj, R.array.spinner_univercity_massachusetts);

                    majSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

                initSpinner(grdSpin, adGrd, R.array.spinner_grade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter, int array){
        arrayAdapter = ArrayAdapter.createFromResource(MainActivity.this, array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }




}