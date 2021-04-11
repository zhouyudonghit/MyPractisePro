package com.example.localuser.retrofittest.DragSortListView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

public class DslvTestActivity extends AppCompatActivity implements DSLVFragment.SetConfig{
    private DSLVFragment mFragment;
    private CommonAdapter mAdapter;
    private List<Integer> mDatas = new ArrayList<>();
    public static String TAG_PREFIX = "DslvTest---";
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dslv_main);
        initData();
    }

    public void initData()
    {
        for(int i = 0; i < 5;i++)
        {
            mDatas.add(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragment = new DSLVFragment();
        mAdapter = new CommonAdapter<Integer>(this,R.layout.layout_dslv_item,mDatas) {
            @Override
            public void convert(ViewHolder helper, Integer item, int position) {
                TextView dataTv = helper.getView(R.id.data_tv);
                dataTv.setText(item+"");
            }
        };
        mFragment.setDragLayoutId(R.id.drag_tv);
        mFragment.setDropListener(onDrop);
        mFragment.setMyListAdapter(mAdapter);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.root_layout, mFragment);

        transaction.commitAllowingStateLoss();
    }

    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {

        }
    };

    @Override
    public int getDividerHeight() {
        return 10;
    }
}
