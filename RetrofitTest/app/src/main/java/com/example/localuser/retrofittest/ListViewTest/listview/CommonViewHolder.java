package com.example.localuser.retrofittest.ListViewTest.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonViewHolder {
    private View mView;
    public CommonViewHolder(View view)
    {
        mView = view;
        mView.setTag(this);
    }

    public static CommonViewHolder getViewHolder(View contentView, ViewGroup parent, int layoutId, Context context)
    {
        if(contentView == null)
        {
            contentView = LayoutInflater.from(context).inflate(layoutId,null);
            return new CommonViewHolder(contentView);
        }
        return (CommonViewHolder) contentView.getTag();
    }

    public <T extends View> T getViewById(int viewId)
    {
        return mView.findViewById(viewId);
    }

    public View getItemView()
    {
        return mView;
    }
}
