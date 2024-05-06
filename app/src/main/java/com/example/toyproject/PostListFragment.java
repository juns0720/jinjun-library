package com.example.toyproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class PostListFragment extends Fragment {

    private ArrayAdapter<CharSequence> adUniv;
    private Spinner univSpin, majSpin, grdSpin;
    private CategorySpinner categorySpinner;
    private PostListAdapter postAdapter;
    private String[] titles;
    private String[] prices;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titles = new String[50];
        prices = new String[50];
        for(int i = 0; i < 50; i++){
            titles[i] = "제목: "+ i + "번 째 책 팝니다.";
            prices[i] = "가격: " + i + "원";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postlist_fragment, container, false);
        mContext = getActivity();
        univSpin = view.findViewById(R.id.university);
        majSpin = view.findViewById(R.id.major);
        grdSpin = view.findViewById(R.id.grade);



        RecyclerView recyclerView = view.findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        postAdapter = new PostListAdapter(mContext, titles, prices);
        recyclerView.setAdapter(postAdapter);

        adUniv = ArrayAdapter.createFromResource(mContext, R.array.spinner_university, android.R.layout.simple_spinner_dropdown_item);
        adUniv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univSpin.setAdapter(adUniv);

        categorySpinner = new CategorySpinner(mContext, adUniv, univSpin, majSpin, grdSpin);
        categorySpinner.setSpinner();

        return view;
    }
}