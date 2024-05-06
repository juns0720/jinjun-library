package com.example.toyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, joinBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        loginBtn = findViewById(R.id.mainLoginBtn);
        joinBtn = findViewById(R.id.mainJoinBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앱을 꺼도 로그인 한 데이터가 유지되는 로직 필요

                //로그인 성공 로직
                Toast.makeText(getApplicationContext(), "로그인 되었습니다!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainFragment.class);
                startActivity(intent);
            }

        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });



        }


    }
