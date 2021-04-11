package com.example.localuser.retrofittest.ListViewTest.recyclelistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.localuser.retrofittest.ListViewTest.listview.CommonViewHolder;

import java.util.List;

public abstract class BaseCommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonRecycleViewHolder> {
    private List<T> mDatas;
    private Context mContext;
    private int mLayoutId;

    public BaseCommonRecycleAdapter(int layoutId, List<T> datas, Context context)
    {
        mLayoutId = layoutId;
        mDatas = datas;
        mContext = context;
    }

    @NonNull
    @Override
    public CommonRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return CommonRecycleViewHolder.getViewHolder(mContext,viewGroup,mLayoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecycleViewHolder commonRecycleViewHolder, int i) {
        convertView(commonRecycleViewHolder,mDatas.get(i),i);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convertView(CommonRecycleViewHolder commonRecycleViewHolder,T data, int position);

    public void updateDatas(List<T> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
