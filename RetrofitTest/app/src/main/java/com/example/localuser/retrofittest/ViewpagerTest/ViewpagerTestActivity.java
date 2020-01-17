package com.example.localuser.retrofittest.ViewpagerTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.PullRefreshListView.Configs;
import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_TABLAYOUT + getClass().getSimpleName();
    private ViewPager mViewpager;
    private TabLayout mTabLayout;
    private List<BaseFragment> mFragmentList;
    private FragmentPagerAdapter mViewPagerAdapter;
    private List<String> mTitleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager_test_main);
        mViewpager = findViewById(R.id.my_viewpager);
        mTabLayout = findViewById(R.id.my_tablayout);
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡一").setIcon(R.mipmap.ic_launcher));
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡二").setIcon(R.mipmap.ic_launcher));
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡三").setIcon(R.mipmap.ic_launcher));
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡四").setIcon(R.mipmap.ic_launcher));
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡五").setIcon(R.mipmap.ic_launcher));
//        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡六").setIcon(R.mipmap.ic_launcher));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡一"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡二"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡三"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡四"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡五"));
        mTabLayout.addTab(mTabLayout.newTab().setText("选项卡六"));
        initViews();
    }

    public void initViews()
    {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(MyFragment1.getInstance("frag1"));
        mFragmentList.add(MyFragment2.getInstance("frag2"));
        mFragmentList.add(MyFragment3.getInstance("frag3"));
        mFragmentList.add(MyFragment4.getInstance("frag4"));
        mFragmentList.add(MyFragment5.getInstance("frag5"));
        mFragmentList.add(MyFragment6.getInstance("frag6"));

        mTitleList = new ArrayList<>();
        for(BaseFragment fragment:mFragmentList)
        {
            mTitleList.add("frag1");
            mTitleList.add("frag2");
            mTitleList.add("frag3");
            mTitleList.add("frag4");
            mTitleList.add("frag5");
            mTitleList.add("frag6");
        }

        mViewPagerAdapter = new MyViewPageAdatper(getSupportFragmentManager(),mFragmentList);
        mViewpager.setAdapter(mViewPagerAdapter);
        mViewpager.setOffscreenPageLimit(2);//预加载
        mTabLayout.setupWithViewPager(mViewpager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG,"onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG,"onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG,"onTabReselected");
            }
        });
    }

    public class MyViewPageAdatper extends FragmentPagerAdapter
    {
        private List<BaseFragment> mFragList;
        public MyViewPageAdatper(FragmentManager fm) {
            super(fm);
        }
        public MyViewPageAdatper(FragmentManager fm,List<BaseFragment> fragList)
        {
            super(fm);
            mFragList = fragList;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragList.get(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragList == null ? 0 : mFragList.size();
        }
    }
}
