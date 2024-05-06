package com.example.bookmarket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PostBoard extends AppCompatActivity {
    Uri uri;
    ImageView imageView;
    EditText eTextTitle, eTextPrice, eTextDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_board);
        Button b = (Button) findViewById(R.id.button1);
        ImageButton img = (ImageButton) findViewById(R.id.imageButton);
        RadioButton r1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        // 첫 번째 스피너, 두 번째 스피너, 세 번째 스피너의 참조 가져오기
        Spinner firstSpinner = (Spinner) findViewById(R.id.spinner_university);
        Spinner secondSpinner = (Spinner) findViewById(R.id.spinner_department);
        Spinner thirdSpinner = (Spinner) findViewById(R.id.spinner_grade);

// 첫 번째 스피너의 어댑터 설정
        firstSpinner.setAdapter(new ArrayAdapter<>(
                PostBoard.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner_univercity)
        ));

        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 대학교에 따라 두 번째 스피너의 옵션 변경
                if (position == 0) { // 성결대학교
                    secondSpinner.setAdapter(new ArrayAdapter<>(
                            PostBoard.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_univercity_sungkyul)
                    ));
                } else if (position == 1) { // 서울대학교
                    secondSpinner.setAdapter(new ArrayAdapter<>(
                            PostBoard.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_univercity_seoul)
                    ));
                } else if (position == 2) { // 고려대학교
                    secondSpinner.setAdapter(new ArrayAdapter<>(
                            PostBoard.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_univercity_massachusetts)
                    ));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않은 경우 처리
            }
        });

// 세 번째 스피너의 어댑터 설정
        thirdSpinner.setAdapter(new ArrayAdapter<>(
                PostBoard.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner_grade)
        ));


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });
    }
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();

                            // ImageView에 선택한 사진 설정
                            imageView.setImageURI(selectedImageUri);

                            // ImageView를 visible로 변경
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });



}