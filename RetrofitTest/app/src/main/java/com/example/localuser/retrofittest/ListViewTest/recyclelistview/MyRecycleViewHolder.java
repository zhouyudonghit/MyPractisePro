package com.example.localuser.retrofittest.ListViewTest.recyclelistview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class MyRecycleViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public MyRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.data_tv);
    }
}
