package com.example.localuser.retrofittest.DialogView;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localuser.retrofittest.R;

public class MyDialog extends Dialog {
    private Context mContext;
    public MyDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public void init()
    {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_view,null);
        //setContentView(view);

        View view2  = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_view2,null);
        addContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addContentView(view2,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


}
