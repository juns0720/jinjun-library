package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);
        setDefaultFragment(new PostListFragment());


        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.fragment_chat) {

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                ft.replace(R.id.frame_container, new ChatListFragment(), "ChatList");
                ft.commitAllowingStateLoss();
            } else if (itemId == R.id.fragment_myinfo) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                ft.replace(R.id.frame_container, new UserInfoFragment(), "UserInfo");
                ft.commitAllowingStateLoss();
            } else if (itemId == R.id.fragment_search) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                ft.replace(R.id.frame_container, new SearchFragment(), "Search");
                ft.commitAllowingStateLoss();
            } else if (itemId == R.id.fragment_home) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                ft.replace(R.id.frame_container, new PostListFragment(), "PostList");
                ft.commitAllowingStateLoss();
            }
            return true;
        });
    }

    public void setDefaultFragment(Fragment FirstFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, FirstFragment);
        ft.commit();
    }
}