package com.example.localuser.retrofittest.autosleep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class EventViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textview);
    }
}
