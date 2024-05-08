package com.example.toyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {
    private String[] titles;
    private String[] prices;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    PostListAdapter(Context context, String[] titles, String[] prices){
        this.mInflater = LayoutInflater.from(context);
        this.titles = titles;
        this.prices = prices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.main_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.ViewHolder holder, int position) {
        holder.title.setText(titles[position]);
        holder.price.setText(prices[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
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
        return titles[id];
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
