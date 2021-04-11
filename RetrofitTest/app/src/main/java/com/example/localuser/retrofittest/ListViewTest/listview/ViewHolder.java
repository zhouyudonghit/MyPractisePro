package com.example.localuser.retrofittest.ListViewTest.listview;

import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class ViewHolder {
    public TextView mTextView;

    public ViewHolder(View view)
    {
        mTextView = view.findViewById(R.id.data_tv);
    }
}
