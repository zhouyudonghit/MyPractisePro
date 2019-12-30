package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

public class PullRefreshRecyclerViewActivity extends AppCompatActivity {
    public static String TAG_PREX = "PullRefreshRecyclerViewActivity--";
    private String TAG = TAG_PREX+getClass().getSimpleName();
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrefresh_recyclerview_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mDatas = new ArrayList<>();

        adapter = new MyAdapter(this,mDatas);
        recyclerView.setAdapter(adapter);
        setdata();
    }

    public void setdata()
    {
        mDatas.add("item 0");
        mDatas.add("item 1");
        mDatas.add("item 2");
        mDatas.add("item 3");
        mDatas.add("item 4");
        mDatas.add("item 5");
        mDatas.add("item 6");
        mDatas.add("item 7");
        mDatas.add("item 8");
        mDatas.add("item 9");
        mDatas.add("item 10");
        mDatas.add("item 11");
        mDatas.add("item 12");
        mDatas.add("item 13");
        mDatas.add("item 14");
        mDatas.add("item 15");
        mDatas.add("item 16");
        mDatas.add("item 17");
        mDatas.add("item 18");
        mDatas.add("item 19");
        mDatas.add("item 20");
        adapter.setDatas(mDatas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onresume()");
        Log.d(TAG,"recyclerView.getWidth() = "+recyclerView.getWidth()+",recyclerView.getHeight() = "+recyclerView.getHeight());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG,"onWindowFocusChanged()");
        Log.d(TAG,"recyclerView.getWidth() = "+recyclerView.getWidth()+",recyclerView.getHeight() = "+recyclerView.getHeight());
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        MyDividerDrawable dividerDarwble = new MyDividerDrawable();
        //dividerDarwble.setBounds();
        divider.setDrawable(dividerDarwble);
        //divider.setDrawable(getDrawable(R.drawable.recycleview_divider_drawable));
        recyclerView.addItemDecoration(divider);
        recyclerView.getChildCount();
        int i = 0;
    }
}
