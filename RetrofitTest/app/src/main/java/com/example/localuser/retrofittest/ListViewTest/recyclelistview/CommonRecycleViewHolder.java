package com.example.localuser.retrofittest.ListViewTest.recyclelistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonRecycleViewHolder extends RecyclerView.ViewHolder {
    public CommonRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static CommonRecycleViewHolder getViewHolder(Context context, ViewGroup parent,int layoutId)
    {
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new CommonRecycleViewHolder(view);
    }

    public <T extends View> T getViewById(int viewId)
    {
        return itemView.findViewById(viewId);
    }
}
