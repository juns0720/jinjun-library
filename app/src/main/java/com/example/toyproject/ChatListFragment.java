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
import android.widget.ImageView;
import android.widget.Toast;


public class ChatListFragment extends Fragment {
    Context mContext;
    ImageView chatIconImage;
    ChatListAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatlist_fragment, container, false);
        mContext = getActivity();
        chatIconImage = view.findViewById(R.id.testImage);

        String[] data = new String[100];
        for(int i=1;i<=100;i++) {
            data[i-1] = "friend #"+i;
        }
        RecyclerView recyclerView = view.findViewById(R.id.chatListRView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ChatListAdapter(mContext, data);

        //리사이클러뷰 아이템 상호작용 로직

        recyclerView.setAdapter(adapter);

        chatIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }


}