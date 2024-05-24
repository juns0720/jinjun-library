package com.example.toyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {
    Context mContext;
    ChatListAdapter adapter;
    private FirebaseFirestore db;
    ArrayList<String> nameData, statusData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatlist_fragment, container, false);
        db = FirebaseFirestore.getInstance();
        mContext = getActivity();
        nameData = new ArrayList<>();
        statusData = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.chatListRView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ChatListAdapter(mContext, nameData, statusData);
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰 아이템 상호작용 로직
        adapter.setClickListener(new ChatListAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                startActivity(intent);
            }
        });

        // Firestore에서 데이터 가져오기
        getUsersFromFirestore();

        return view;
    }

    private void getUsersFromFirestore() {
        db.collection("ChatUserList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Firestore", document.getId() + " => " + document.getData());
                                // 문서 데이터 처리
                                nameData.add(document.getString("name"));
                                statusData.add(document.getString("status"));
                            }
                            // 어댑터에 데이터가 변경되었음을 알림
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("Firestore", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
