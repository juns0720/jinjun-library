package com.example.toyproject;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.Objects;


public class CategorySpinner{
    private Context context;
    private ArrayAdapter<CharSequence> adUniv, adGrd, adMaj;
    private Spinner univSpin, majSpin, grdSpin;

    CategorySpinner(Context context, ArrayAdapter<CharSequence> adUniv, Spinner univSpin, Spinner majSpin,Spinner grdSpin){
        this.context = context;
        this.adUniv = adUniv;
        this.univSpin = univSpin;
        this.majSpin = majSpin;
        this.grdSpin = grdSpin;
    }

    public void setSpinner(){
        univSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(Objects.equals(adUniv.getItem(position), "성결대학교")){
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

                } else if (Objects.equals(adUniv.getItem(position), "서울대학교")) {
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
                } else if (Objects.equals(adUniv.getItem(position), "고려대학교")) {
//                    choice_univ = "고려대학교";
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
        arrayAdapter = ArrayAdapter.createFromResource(context, array, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

}
