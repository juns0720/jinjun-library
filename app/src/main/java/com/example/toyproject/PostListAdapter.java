package com.example.toyproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> writers, titles, prices, contents;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    PostListAdapter(Context context,ArrayList<String> writers, ArrayList<String> titles, ArrayList<String> prices, ArrayList<String> contents){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.writers = writers;
        this.titles = titles;
        this.prices = prices;
        this.contents = contents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.main_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = titles.get(position);
        String price = prices.get(position);
        String writer = writers.get(position);
        String content = contents.get(position);

        holder.title.setText("제목: " + title);
        holder.price.setText("가격: " + price);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostActivity.class);
                intent.putExtra("writer", writer);
                intent.putExtra("title", title);
                intent.putExtra("price", price);
                intent.putExtra("content", content);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title;
    TextView price;
    ViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        price = itemView.findViewById(R.id.price);
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View view){
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}



    String getItem(int id) {
        return titles.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
