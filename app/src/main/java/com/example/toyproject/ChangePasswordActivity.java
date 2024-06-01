package com.example.toyproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "nowPassword";

    private Button changePasswordButton;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private Context context;
    private EditText newPassword_et, newPasswordCheck_et, currentPassword_et;
    String newPassword, newPasswordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.change_password);


        context = getApplicationContext();
        newPassword_et = findViewById(R.id.changePassword);
        newPasswordCheck_et = findViewById(R.id.passwordCheck);
        currentPassword_et = findViewById(R.id.currentPassword);

        // 버튼 객체 참조
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                newPassword = newPassword_et.getText().toString();
                newPasswordCheck = newPasswordCheck_et.getText().toString();
                if(newPassword.equals(newPasswordCheck)) {
                    // 비밀번호 변경 로직 , 비밀번호 확인과 일치 로직 수행
                    auth = FirebaseAuth.getInstance();
                    user = auth.getCurrentUser();

                    newPassword = newPassword_et.getText().toString();
                    newPasswordCheck = newPasswordCheck_et.getText().toString();


                    if (newPassword.equals(newPasswordCheck)) {
                        // 현재 사용자의 이메일과 비밀번호를 가져옴
                        String email = user.getEmail();
                        String currentPassword = currentPassword_et.getText().toString(); // 사용자로부터 현재 비밀번호를 입력

                        AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

                        // 재인증
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // 재인증 성공 시
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "암호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Log.d(TAG, "암호 변경 성공");
                                            } else {
                                                Log.d(TAG, "암호 변경 실패");
                                                Toast.makeText(context, "암호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    // 재인증 실패 시
                                    Log.d(TAG, "재인증 실패");
                                    Toast.makeText(context, "현재 암호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else {
                        Toast.makeText(context, "변경하려는 암호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
