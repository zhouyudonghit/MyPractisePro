package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<String> mDatas = new ArrayList<>();
    private Context mContext;

    public MyAdapter(Context context,List datas)
    {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(holder != null)
        {
            holder.textView.setText(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    public void setDatas(List datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
