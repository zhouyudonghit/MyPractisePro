package com.example.localuser.retrofittest.ViewpagerTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public abstract class BaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    public static String PARAM_STRING = "param_string";
    public static String LAYOUT_RES_ID = "layout_res_id";
    protected String mParamString;
    protected int mLayoutResId;
    protected Activity mActivity;
    protected View mView;
    protected TextView mTextView;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach");
        super.onAttach(context);
        if(context instanceof Activity)
        {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mParamString = bundle.getString(PARAM_STRING);
        mLayoutResId = bundle.getInt(LAYOUT_RES_ID);
    }

    @Override
    public void onResume() {
        Log.d(TAG,"onResume()");
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        mView = inflater.inflate(mLayoutResId == -1 ? R.layout.layout_viewpager_fragment_main:mLayoutResId,container,false);
        initViews();
        return mView;
    }

    public void initViews()
    {
        mTextView = mView.findViewById(R.id.my_textview);
        mTextView.setText(mParamString);
    }

    @Override
    public void onStart() {
        Log.d(TAG,"onStart()");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d(TAG,"onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG,"onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG,"onDetach()");
        super.onDetach();
    }

    public String getParamString() {
        return mParamString;
    }

    public void setParamString(String mParamString) {
       this.mParamString = mParamString;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public void setLayoutResId(int mLayoutResId) {
        this.mLayoutResId = mLayoutResId;
    }
}
