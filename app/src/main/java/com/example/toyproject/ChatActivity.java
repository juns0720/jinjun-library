package com.example.toyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class ChatActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    EditText content_et;
    Button send_iv;
    RecyclerView rv;
    ChatAdapter mAdapter;
    String chatroom = "";
    List<ChatMsgVO> msgList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore mStore;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();


        content_et = findViewById(R.id.content_et);
        send_iv = findViewById(R.id.send_iv);
        rv = findViewById(R.id.chatRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //비동기 방식으로 유저의 닉네임 받아서 어댑터 매개변수에 추가
        mStore.collection(FirebaseID.user).document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        userId = document.getString(FirebaseID.nickname);
                        mAdapter = new ChatAdapter(msgList,userId);
                        rv.setAdapter(mAdapter);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        ImageView backImage = findViewById(R.id.back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        myRef = database.getReference("message");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMsgVO chatMsgVO = dataSnapshot.getValue(ChatMsgVO.class);
                msgList.add(chatMsgVO);
                mAdapter.notifyDataSetChanged();
                rv.scrollToPosition(msgList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        send_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        initChatView();
    }

    private void initChatView() {

    }

    private void sendMessage() {
        String message = content_et.getText().toString().trim();

        if (!message.isEmpty()) {
            ChatMsgVO msgVO = new ChatMsgVO(userId, getCurrentDateTime(), message);
            myRef.push().setValue(msgVO);
            content_et.setText("");
            Toast.makeText(this, userId+"님이 메세지를 입력했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "메시지를 입력하세요.", Toast.LENGTH_SHORT).show();
        }

    }


    private String getCurrentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }



}






