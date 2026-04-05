package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.localuser.retrofittest.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textview);
    }


}
