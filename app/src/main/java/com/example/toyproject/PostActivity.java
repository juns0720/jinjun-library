package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {
    TextView nickname, price, title, content;
    ImageView photo1,photo2,photo3,photo4,photo5,backArrow;
    Button trade;
    int boardId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_item);
        context = getApplicationContext();
        //객체 연결
        photo1 = findViewById(R.id.post_iv_photo1);
        photo2 = findViewById(R.id.post_iv_photo2);
        photo3 = findViewById(R.id.post_iv_photo3);
        photo4 = findViewById(R.id.post_iv_photo4);
        photo5 = findViewById(R.id.post_iv_photo5);
        nickname = findViewById(R.id.post_tv_nickname);
        price = findViewById(R.id.post_tv_price);
        title = findViewById(R.id.post_tv_title);
        content = findViewById(R.id.post_tv_content);
        trade = findViewById(R.id.post_btn_trade);
        backArrow = findViewById(R.id.post_iv_backarrow);

        //인텐트로부터 정보 받아오기
        boardId = getIntent().getIntExtra("id", 0);

        /*
        //DB로 부터 값 입력하기.
        //DB 연결

        //값 입력
        photo1.setImageResource();
        photo2.setImageResource();
        photo3.setImageResource();
        photo4.setImageResource();
        photo5.setImageResource();
        */
        nickname.setText(getIntent().getStringExtra("writer"));
        price.setText(getIntent().getStringExtra("price"));
        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));


        //trade 버튼
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}