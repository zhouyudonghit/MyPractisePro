package com.example.localuser.retrofittest.DragSortListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyd on 2016/5/13.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> dataList;
    private Context mContext;
    private int resId;
    private List<T> oldDataList;
    private List tmpDataList;
    private int startDragIndex;
    private int endDragIndex;

    public CommonAdapter(Context context, int resId, List<T> dataList) {

        this.dataList = dataList;
        tmpDataList = this.dataList;
        createDataCopy();
        initStartAndEndIndex();
        this.mContext = context;
        this.resId = resId;
    }

    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item,int position);

    private ViewHolder getViewHolder(int position, View convertView,
                                       ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, resId,
                position);
    }

    public void remove(T item)
    {
        if(dataList != null && dataList.size() > 0)
        {
            dataList.remove(item);
            this.notifyDataSetChanged();
        }
    }

    public void insert(T item,int to)
    {
        if(dataList != null)
        {
            dataList.add(to,item);
        }
        notifyDataSetChanged();
    }

    public List getDataList()
    {
        return dataList;
    }

    public void setDataList(List<T> list)
    {
        this.dataList = list;
    }

    public void saveSort()
    {
        tmpDataList = dataList;
        createDataCopy();
    }

    public void cancelSort()
    {
        dataList = null;
        dataList = oldDataList;
        notifyDataSetChanged();
    }

    public void createDataCopy()
    {
        if(oldDataList == null)
        {
            oldDataList = new ArrayList<>();
        }else{
            oldDataList = tmpDataList;
            oldDataList.clear();
        }
        for(T item:dataList)
        {
            oldDataList.add(item);
        }
    }

    public int getEndDragIndex()
    {
        return endDragIndex;
    }

    public void setEndDragIndex(int i)
    {
        endDragIndex = i;
    }

    public int getStartDragIndex()
    {
        return startDragIndex;
    }

    public void setStartDragIndex(int i)
    {
        startDragIndex = i;
    }

    public Context getmContext()
    {
        return mContext;
    }

    public void initStartAndEndIndex()
    {
        setStartDragIndex(0);
        if(dataList != null )
        {
            setEndDragIndex(dataList.size()-1);
        }
    }
}
