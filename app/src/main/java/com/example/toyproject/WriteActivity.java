package com.example.toyproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity {
    Uri uri;
    Button back, write;
    ImageButton img;
    ImageView imageView;
    EditText et_title, et_price, et_content;
    Spinner sp_university, sp_major, sp_grade;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    String writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.write_activity);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        getUserNickName();
        back = findViewById(R.id.back);
        write = findViewById(R.id.write);
        img = findViewById(R.id.imageButton);
        et_title = findViewById(R.id.editTextTitle);
        et_price = findViewById(R.id.editTextPrice);
        et_content = findViewById(R.id.editTextDetail);


        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        // 첫 번째 스피너, 두 번째 스피너, 세 번째 스피너의 참조 가져오기
        sp_university = findViewById(R.id.spinner_university);
        sp_major = findViewById(R.id.spinner_department);
        sp_grade = findViewById(R.id.spinner_grade);

// 첫 번째 스피너의 어댑터 설정
        sp_university.setAdapter(new ArrayAdapter<>(
                WriteActivity.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner_university)
        ));

        sp_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 대학교에 따라 두 번째 스피너의 옵션 변경
                if (position == 0) { // 성결대학교
                    sp_major.setAdapter(new ArrayAdapter<>(
                            WriteActivity.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_university_sungkyul)
                    ));
                } else if (position == 1) { // 서울대학교
                    sp_major.setAdapter(new ArrayAdapter<>(
                            WriteActivity.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_university_seoul)
                    ));
                } else if (position == 2) { // 고려대학교
                    sp_major.setAdapter(new ArrayAdapter<>(
                            WriteActivity.this, android.R.layout.simple_spinner_item,
                            getResources().getStringArray(R.array.spinner_university_korea)
                    ));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않은 경우 처리
            }
        });

// 세 번째 스피너의 어댑터 설정
        sp_grade.setAdapter(new ArrayAdapter<>(
                WriteActivity.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner_grade)
        ));


        back.setOnClickListener(new View.OnClickListener() {
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

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //게시글 작성 및 데이터 저장 로직
                //이미지 저장 어떻게?
                saveUserToFirestore(v);

//                Toast.makeText(WriteActivity.this, "게시글을 작성했습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveUserToFirestore(View v) {
        final String title = et_title.getText().toString().trim();
        final String tradeType = getTradeType(v);
        final String price = et_price.getText().toString().trim();
        final String content = et_content.getText().toString().trim();
        final String university = sp_university.getSelectedItem().toString().trim();
        final String major = sp_major.getSelectedItem().toString().trim();
        final String grade = sp_grade.getSelectedItem().toString().trim();

        if (title.isEmpty() || price.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "모든 내용을 작성해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> post = new HashMap<>();
        post.put("writer", writer);
        post.put("title", title);
        post.put("tradeType", tradeType);
        post.put("price", price);
        post.put("content", content);
        post.put("university", university);
        post.put("major", major);
        post.put("grade", grade);

        db.collection("PostList")
                .add(post)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(WriteActivity.this, "게시글을 작성했습니다!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(WriteActivity.this, "게시글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }


    public void getUserNickName(){

        db.collection(FirebaseID.user).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        writer = document.getString(FirebaseID.nickname);
                    }
                }
            }
        });

    }


    public String getTradeType(View v){
        RadioButton sell = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton donate = (RadioButton) findViewById(R.id.radioButton2);
        String type = "";
        if(sell.isChecked())
            type = "판매";
        else type = "나눔";
        return type;
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