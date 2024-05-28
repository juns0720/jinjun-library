package com.example.toyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangeNameActivity extends AppCompatActivity {

    private static final String TAG = "nicknameChange";

    private Button changeNicknameButton;
    private EditText newNicknameEditText;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String newNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.change_name);

        // Firebase 인스턴스 초기화
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 버튼 객체 참조
        changeNicknameButton = findViewById(R.id.changeNicknameButton);
        newNicknameEditText = findViewById(R.id.changeNickname);

        changeNicknameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닉네임 변경 로직 수행
                changeUserNickname();
            }
        });
    }

    private void changeUserNickname() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            newNickname = newNicknameEditText.getText().toString().trim();

            if (!newNickname.isEmpty()) {
                DocumentReference userDocRef = db.collection("user").document(uid);
                userDocRef.update("nickname", newNickname)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangeNameActivity.this, "닉네임이 변경되었습니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "닉네임 변경 성공");

                                    // 로그아웃 후 로그인 화면으로 이동
                                    auth.signOut();
                                    Intent intent = new Intent(ChangeNameActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ChangeNameActivity.this, "닉네임 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "닉네임 변경 실패", task.getException());
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "새 닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
