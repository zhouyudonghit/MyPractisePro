package com.example.localuser.retrofittest.ListViewTest.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class BaseCommonAdapter<T> extends BaseAdapter{
    private List<T> mDatas;
    private Context mContext;
    private int mLayoutId;
    public BaseCommonAdapter(int layoutId, List<T> datas, Context context)
    {
        mLayoutId = layoutId;
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(convertView,parent,mLayoutId,mContext);
        convertView(viewHolder,mDatas.get(position),position);
        return viewHolder.getItemView();
    }

    public abstract void convertView(CommonViewHolder viewHolder,T data,int position);

    public void updateDatas(List<T> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
