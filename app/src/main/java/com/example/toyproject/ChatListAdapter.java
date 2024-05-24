package com.example.toyproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> nameData,statusData;
    private ClickListener clickListener;

    public ChatListAdapter(Context context, ArrayList<String> nameData, ArrayList<String> statusData) {
        this.context = context;
        this.nameData = nameData;
        this.statusData = statusData;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = nameData.get(position);
        String status = statusData.get(position);

        // 프로필 사진 설정
        holder.profileImageView.setImageResource(R.drawable.profile_placeholder); // 임시 프로필 사진

        // 이름 설정
        holder.nameTextView.setText(name);

        // 상태 메시지 설정
        holder.statusTextView.setText(status); // 임시 상태 메시지

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(v, position);
            }
            Intent intent = new Intent(context, ChatActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }

    public String getItem(int position) {
        return nameData.get(position);
    }

    public void setClickListener(ChatListFragment chatListFragment) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;
        TextView statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}


