package com.example.localuser.retrofittest.ListViewTest.recyclelistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localuser.retrofittest.R;

import java.util.List;

public class MyRecycleAdapter<T> extends RecyclerView.Adapter<MyRecycleViewHolder> {
    private List<T> mDatas;
    private Context mContext;
    private int mLayoutId;

    public MyRecycleAdapter(List<T> datas, Context context)
    {
        mDatas = datas;
        mContext = context;
    }

    @NonNull
    @Override
    public MyRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dslv_item,viewGroup,false);这种写法是不正确的，应该是下面的写法
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dslv_item,viewGroup,false);
        MyRecycleViewHolder viewHolder = new MyRecycleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewHolder myRecycleViewHolder, int i) {
        myRecycleViewHolder.textView.setText((String)mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
