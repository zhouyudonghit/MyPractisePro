package com.example.localuser.retrofittest.PullRefreshListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/24.
 */

public class PullRefreshActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "PullRefresh--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private PullRefreshListView mPullRefreshListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pullrefresh_activity_main);
        mPullRefreshListView = (PullRefreshListView) findViewById(R.id.pullrefresh_listview);
        mPullRefreshListView.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullRefreshActivity.this,"position = "+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mPullRefreshListView.initView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent"+ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent"+event.toString());
        return super.onTouchEvent(event);
    }
}
