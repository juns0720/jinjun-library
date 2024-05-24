package com.example.toyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostListFragment extends Fragment {

    private ArrayAdapter<CharSequence> adUniv;
    private Spinner univSpin, majSpin, grdSpin;
    private CategorySpinner categorySpinner;
    private PostListAdapter postAdapter;
    private ArrayList<String> writers, titles, prices, contents;
    private Context mContext;
    private Button writeBtn;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postlist_fragment, container, false);
        db = FirebaseFirestore.getInstance(); // Firestore 초기화
        titles = new ArrayList<>();
        prices = new ArrayList<>();
        writers = new ArrayList<>();
        contents = new ArrayList<>();
        mContext = getActivity();
        univSpin = view.findViewById(R.id.university);
        majSpin = view.findViewById(R.id.major);
        grdSpin = view.findViewById(R.id.grade);
        writeBtn = view.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WriteActivity.class);
                startActivity(intent);
            }
        });
        getPostListFromFirestore(); // Firestore에서 데이터 가져오기
        RecyclerView recyclerView = view.findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        postAdapter = new PostListAdapter(mContext, writers, titles, prices, contents);
        recyclerView.setAdapter(postAdapter);

        adUniv = ArrayAdapter.createFromResource(mContext, R.array.spinner_university, android.R.layout.simple_spinner_dropdown_item);
        adUniv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(adUniv);

        categorySpinner = new CategorySpinner(mContext, adUniv, univSpin, majSpin, grdSpin);
        categorySpinner.setSpinner();



        return view;
    }

    private void getPostListFromFirestore() {
        db.collection("PostList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                writers.add(document.getString("writer"));
                                titles.add(document.getString("title"));
                                prices.add(document.getString("price"));
                                contents.add(document.getString("content"));
                            }
                            postAdapter.notifyDataSetChanged(); // 데이터 변경을 알리기 위해 추가
                        }
                    }
                });
    }
}
