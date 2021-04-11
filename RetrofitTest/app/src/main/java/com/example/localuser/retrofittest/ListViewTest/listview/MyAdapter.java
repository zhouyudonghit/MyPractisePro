package com.example.localuser.retrofittest.ListViewTest.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.localuser.retrofittest.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<String> mDatas;
    private Context mContext;

    public MyAdapter(List<String> datas, Context context)
    {
        mDatas = datas;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mDatas == null ? 0:mDatas.size();
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
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_dslv_item,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mDatas.get(position));
        return convertView;
    }
}
