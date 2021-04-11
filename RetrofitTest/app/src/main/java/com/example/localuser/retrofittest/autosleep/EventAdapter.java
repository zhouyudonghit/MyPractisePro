package com.example.localuser.retrofittest.autosleep;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.DateUtil;

import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<SleepEvent> mDatas;

    public EventAdapter(List<SleepEvent> datas)
    {
        mDatas = datas;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_event_item,viewGroup,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        eventViewHolder.mTextView.setText(DateUtil.format(mDatas.get(i).time,DateUtil.DATE_PATTERN_1) +"\n"+ mDatas.get(i).myString());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void updateDatas(List<SleepEvent> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
