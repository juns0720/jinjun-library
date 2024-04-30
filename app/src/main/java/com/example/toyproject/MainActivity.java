package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{
    private ArrayAdapter<CharSequence> adUniv;
    private Spinner univSpin, majSpin, grdSpin;
    private MainCategorySpinner categorySpinner;
    private MainPostAdapter postAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_display);

        String[] titles = new String[50];
        String[] prices = new String[50];
        for(int i = 0; i < 50; i++){
            titles[i] = "제목: "+ i + "번 째 책 팝니다.";
            prices[i] = "가격: " + i + "원";
        }
        univSpin = findViewById(R.id.university);
        majSpin = findViewById(R.id.major);
        grdSpin = findViewById(R.id.grade);

        RecyclerView recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new MainPostAdapter(this, titles, prices);
        recyclerView.setAdapter(postAdapter);

        adUniv = ArrayAdapter.createFromResource(this, R.array.spinner_univercity, android.R.layout.simple_spinner_dropdown_item);
        adUniv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(adUniv);

        categorySpinner = new MainCategorySpinner(this, adUniv, univSpin, majSpin, grdSpin);
        categorySpinner.setSpinner();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.fragment_chat){
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
            else if(itemId == R.id.fragment_myinfo){

            }
            else if(itemId == R.id.fragment_search){

            }
            else if(itemId == R.id.fragment_write){

            }
            return true;
        });
    }








}