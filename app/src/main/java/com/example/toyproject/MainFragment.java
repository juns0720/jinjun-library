package com.example.toyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore mStore;
    private String userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        mStore.collection(FirebaseID.user).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        userNickname = document.getString(FirebaseID.nickname);

                        Bundle bundle = new Bundle();
                        bundle.putString("userNickname", userNickname);

                        // 초기 프래그먼트 설정
                        if (savedInstanceState == null) {
                            Fragment postListFragment = new PostListFragment();
                            postListFragment.setArguments(bundle);
                            setDefaultFragment(postListFragment);
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
                                selectedFragment.setArguments(bundle);
                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction ft = manager.beginTransaction();
                                ft.replace(R.id.frame_container, selectedFragment, tag);
                                ft.addToBackStack(null);
                                ft.commitAllowingStateLoss();
                            }
                            return true;
                        });

                    }
                }
            }
        });


    }

    public void setDefaultFragment(Fragment firstFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, firstFragment);
        ft.commit();
    }
}
