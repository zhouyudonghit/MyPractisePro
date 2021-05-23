package com.example.localuser.retrofittest.ListViewTest;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.system.Os;
import android.util.Log;
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
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.ItemDecor;
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.MyRecycleAdapter;
import com.example.localuser.retrofittest.R;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;

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
    private LinearLayoutManager mLinearLayoutManager;

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
//                mDatas.add("new data");
//                mBaseCommonAdapter.updateDatas(mDatas);
//                mBaseCommonRecycleAdapter.updateDatas(mDatas);
                testMoveToCertainPosition();
            }
        });
        initDatas();
//        initListView();
        initRecycleListView();
        testSystemDemoActivity();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
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
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLinearLayoutManager);//        mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0,0,0, AppUtils.dp2px(10));
//            }
//        });
//        mRecycleView.addItemDecoration(new DividerItemDecoration(this,1){
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.set(0,0,0,AppUtils.dp2px(10));
//            }
//        });
        mRecycleView.addItemDecoration(new ItemDecor());
        mRecycleView.setOverScrollMode(OVER_SCROLL_NEVER);
//        myRecycleAdapter = new MyRecycleAdapter<>(mDatas,this);
        mBaseCommonRecycleAdapter = new BaseCommonRecycleAdapter<String>(R.layout.layout_dslv_item,mDatas,this) {
            @Override
            public void convertView(CommonRecycleViewHolder commonRecycleViewHolder, String data, int position) {
                TextView dataView = commonRecycleViewHolder.getViewById(R.id.data_tv);
                dataView.setText(data);
            }
        };
        mRecycleView.setAdapter(mBaseCommonRecycleAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG,"onScrolled,mRecycleView.getChildCount() = "+mRecycleView.getChildCount());
            }
        });
        Log.d(TAG,"mRecycleView.getChildCount() = "+mRecycleView.getChildCount());
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
        mDatas.add("8");
        mDatas.add("9");
        mDatas.add("10");
        mDatas.add("11");
        mDatas.add("12");
        mDatas.add("13");
        mDatas.add("14");
        mDatas.add("15");
        mDatas.add("16");
        mDatas.add("17");
        mDatas.add("18");
        mDatas.add("19");
        mDatas.add("20");
        mDatas.add("21");
    }

    private void testMoveToCertainPosition()
    {
        //当滑动到最后一项，下句代码有效，且有平滑效果
//        mRecycleView.smoothScrollToPosition(0);
        //当跳转位置在第一个可见项之后，最后一个可见项之前smoothScrollToPosition()不会滚动
//        mRecycleView.smoothScrollToPosition(10);
        //如果要跳转的位置在最后可见项之后，会平滑滚动，直至出现在最后一项可见位置
//        mRecycleView.smoothScrollToPosition(13);
        mLinearLayoutManager.scrollToPositionWithOffset(0,0);
    }

    private void testSystemDemoActivity()
    {

    }
}