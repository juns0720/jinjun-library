package com.example.toyproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserInfoFragment extends Fragment {
    private Button logoutBtn;
    private FirebaseAuth auth;
    private Activity activity;

    private Button changeNicknameButton;
    private Button changePasswordButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.userinfo_fragment, container, false);
        activity = getActivity();

        logoutBtn = v.findViewById(R.id.userinfo_tv_logout);
        changeNicknameButton = v.findViewById(R.id.userInfo_tv_chNick);
        changePasswordButton = v.findViewById(R.id.userInfo_tv_chPw);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                Toast.makeText(activity, user.getEmail() + "님이 로그아웃했습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, MainActivity.class);

                activity.finish();
                startActivity(intent);
                auth.signOut();
            }
        });

        changeNicknameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닉네임 변경 화면으로 이동
                navigateToChangeNameActivity();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 닉네임 변경 화면으로 이동
                navigateToChangePasswordActivity();
            }
        });

        return v;
    }

    private void navigateToChangeNameActivity() {
        Intent intent = new Intent(getContext(), ChangeNameActivity.class);
        startActivity(intent);
    }

    private void navigateToChangePasswordActivity() {
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }
}
