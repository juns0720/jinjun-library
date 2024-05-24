package com.example.toyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, joinBtn;
    EditText et_userId, et_userpw;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        loginBtn = findViewById(R.id.mainLoginBtn);
        joinBtn = findViewById(R.id.mainJoinBtn);
        et_userId = findViewById(R.id.userId_et);
        et_userpw = findViewById(R.id.userPw_et);

        mAuth = FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_userId.getText().toString().trim();
                String password = et_userpw.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()){
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainFragment.class);
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //로그인 성공 로직
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
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user){
        if( user!= null){
            Intent intent = new Intent(this,MainFragment.class);
            startActivity(intent);
            Toast.makeText(this,user.getEmail()+"님 환영합니다!",Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
