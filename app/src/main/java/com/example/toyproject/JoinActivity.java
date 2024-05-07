package com.example.toyproject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    Button joinBtn;
    Spinner univSpin, majorSpin;
    EditText et_joinId, et_joinPw, et_joinName, et_joinUniv, et_joinMajor;
    ArrayAdapter<CharSequence> categoryAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;


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

        mStore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        et_joinId = findViewById(R.id.editId);
        et_joinPw = findViewById(R.id.editPw);
        et_joinName = findViewById(R.id.editName);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = et_joinId.getText().toString().trim();
                final String password = et_joinPw.getText().toString().trim();
                final String nickname = et_joinName.getText().toString().trim();
                final String univ = univSpin.getSelectedItem().toString().trim();
                final String major = majorSpin.getSelectedItem().toString().trim();

                if ((email != null) && !email.isEmpty() && (password != null) && !password.isEmpty() && (nickname != null) && !nickname.isEmpty() ){
                    mAuth.createUserWithEmailAndPassword(et_joinId.getText().toString(),et_joinPw.getText().toString()).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();

                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put(FirebaseID.documentId, user.getUid());
                                userMap.put(FirebaseID.email, email);
                                userMap.put(FirebaseID.password, password);
                                userMap.put(FirebaseID.nickname, nickname);
                                userMap.put(FirebaseID.univ, univ);
                                userMap.put(FirebaseID.major, major);

                                mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                //회원가입 성공시 로그인 액티비티로 화면 전환
                                Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다!",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"회원가입에 실패했습니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }



        });
    }

}