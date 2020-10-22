package com.example.localuser.retrofittest.ListViewTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.ListViewTest.listview.BaseCommonAdapter;
import com.example.localuser.retrofittest.ListViewTest.listview.CommonViewHolder;
import com.example.localuser.retrofittest.ListViewTest.listview.MyAdapter;
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.BaseCommonRecycleAdapter;
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.CommonRecycleViewHolder;
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.MyRecycleAdapter;
import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_LISTVIEW + getClass().getSimpleName();
    private ListView mListView;
    private MyAdapter mListAdapter;
    private BaseCommonAdapter mBaseCommonAdapter;
    private List<String> mDatas;
    private RecyclerView mRecycleView;
    private MyRecycleAdapter<String> myRecycleAdapter;
    private BaseCommonRecycleAdapter mBaseCommonRecycleAdapter;
    private Button mChangeData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_main);
        mListView = findViewById(R.id.listview);
        mListView.setVisibility(View.GONE);
        mRecycleView = findViewById(R.id.recycle_list_view);
//        mRecycleView.setVisibility(View.GONE);
        mChangeData = findViewById(R.id.change_data);
        mChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.add("new data");
//                mBaseCommonAdapter.updateDatas(mDatas);
                mBaseCommonRecycleAdapter.updateDatas(mDatas);
            }
        });
        initDatas();
//        initListView();
        initRecycleListView();
    }

    private void initListView()
    {
//        mListAdapter = new MyAdapter(mDatas,this);
        mBaseCommonAdapter = new BaseCommonAdapter<String>(R.layout.layout_dslv_item,mDatas,this) {
            @Override
            public void convertView(CommonViewHolder viewHolder, String data, int position) {
                TextView dataView = viewHolder.getViewById(R.id.data_tv);
                dataView.setText(data);
            }
        };
        mListView.setAdapter(mBaseCommonAdapter);
    }

    private void initRecycleListView()
    {
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        myRecycleAdapter = new MyRecycleAdapter<>(mDatas,this);
        mBaseCommonRecycleAdapter = new BaseCommonRecycleAdapter<String>(R.layout.layout_dslv_item,mDatas,this) {
            @Override
            public void convertView(CommonRecycleViewHolder commonRecycleViewHolder, String data, int position) {
                TextView dataView = commonRecycleViewHolder.getViewById(R.id.data_tv);
                dataView.setText(data);
            }
        };
        mRecycleView.setAdapter(mBaseCommonRecycleAdapter);
    }

    private void initDatas()
    {
        mDatas = new ArrayList<>();
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
        mDatas.add("4");
        mDatas.add("5");
        mDatas.add("6");
        mDatas.add("7");
    }
}
