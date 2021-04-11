package com.example.localuser.retrofittest.MotionEventTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/5/7.
 */

public class MyFragment extends Fragment {
    private String TAG = MotionEventTestActivity.TAG_PREFIX+getClass().getSimpleName();
    private View rootView;

    public static MyFragment getInstance()
    {
        MyFragment fragment = new MyFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        rootView = inflater.inflate(R.layout.layout_motionevent_fragment,container,false);
        return rootView;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach");
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop()");
        super.onStop();
    }



    @Override
    public void onDetach() {
        Log.d(TAG,"onDetach()");
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
