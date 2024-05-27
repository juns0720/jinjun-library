package com.example.toyproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangeNameActivity extends AppCompatActivity {

    private Button changeNicknameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.change_name);

        // 버튼 객체 참조
        changeNicknameButton = findViewById(R.id.changeNicknameButton);

        changeNicknameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닉네임 변경 로직 수행

                finish(); // 현재 액티비티 종료
            }
        });
    }
}