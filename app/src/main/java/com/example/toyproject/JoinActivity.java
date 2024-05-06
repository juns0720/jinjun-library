package com.example.toyproject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class JoinActivity extends AppCompatActivity {
    Button joinBtn;
    Spinner univSpin, majorSpin;
    ArrayAdapter<CharSequence> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);
        joinBtn = findViewById(R.id.mainJoinBtn);

        univSpin = findViewById(R.id.univSpinner);
        majorSpin = findViewById(R.id.majorSpinner);


        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_university, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(categoryAdapter);
        JoinCategorySpinner joinCategorySpinner = new JoinCategorySpinner(this, categoryAdapter, univSpin, majorSpin);
        joinCategorySpinner.setSpinner();







        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 데이터 저장하는 로직 (아이디, 비밀번호 중복 등 예외 처리 + 데이터베이스에 데이터 저장)

                Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}