package com.example.localuser.retrofittest.autosleep;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localuser.retrofittest.R;

public class EventViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.textview);
    }
}
