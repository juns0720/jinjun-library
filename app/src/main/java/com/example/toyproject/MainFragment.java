package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // 초기 프래그먼트 설정
        if (savedInstanceState == null) {
            setDefaultFragment(new PostListFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;
            String tag = null;

            if (itemId == R.id.fragment_chat) {
                selectedFragment = new ChatListFragment();
                tag = "ChatList";
            } else if (itemId == R.id.fragment_myinfo) {
                selectedFragment = new UserInfoFragment();
                tag = "UserInfo";
            } else if (itemId == R.id.fragment_search) {
                selectedFragment = new SearchFragment();
                tag = "Search";
            } else if (itemId == R.id.fragment_home) {
                selectedFragment = new PostListFragment();
                tag = "PostList";
            }

            if (selectedFragment != null && tag != null) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.frame_container, selectedFragment, tag);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
            return true;
        });
    }

    public void setDefaultFragment(Fragment firstFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, firstFragment);
        ft.commit();
    }
}
