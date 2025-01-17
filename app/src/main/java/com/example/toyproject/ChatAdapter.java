package com.example.toyproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final List<ChatMsgVO> mValues;

    String currentUserNickName;

    public ChatAdapter(List<ChatMsgVO> items, String currentUserNickName) {
        mValues = items;
        this.currentUserNickName = currentUserNickName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_recyclerview_msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        ChatMsgVO vo = mValues.get(position);
        if (mValues.get(position).getUserid().equals(currentUserNickName)) {
            holder.other_cl.setVisibility(View.GONE);
            holder.my_cl.setVisibility(View.VISIBLE);
            holder.userid_tv.setText(vo.getUserid());
            holder.date_tv2.setText(vo.getCrt_dt());
            holder.content_tv2.setText(vo.getContent());
        }
        else{
            holder.other_cl.setVisibility(View.VISIBLE);
            holder.my_cl.setVisibility(View.GONE);

            holder.userid_tv.setText(vo.getUserid());
            holder.date_tv.setText(vo.getCrt_dt());
            holder.content_tv.setText(vo.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout my_cl, other_cl;
        public TextView userid_tv, date_tv, content_tv, date_tv2, content_tv2 ;

        public ViewHolder(View view) {
            super(view);
            my_cl = view.findViewById(R.id.my_cl);
            other_cl = view.findViewById(R.id.other_cl);
            userid_tv = view.findViewById(R.id.userid_tv);
            date_tv = view.findViewById(R.id.date_tv);
            content_tv = view.findViewById(R.id.content_tv);
            date_tv2 = view.findViewById(R.id.date_tv2);
            content_tv2 = view.findViewById(R.id.content_tv2);

        }

    }
}