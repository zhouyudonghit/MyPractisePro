package com.example.localuser.retrofittest.ViewpagerTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class ViewpagerTestActivity extends AppCompatActivity {
    private ViewPager mViewpager;
    private TabLayout mTabLayout;
    private List<BaseFragment> mFragmentList;

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
        mFragmentList.add(My)
        mViewpager.set
    }

    public class MyViewPageAdatper extends FragmentPagerAdapter
    {

        public MyViewPageAdatper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
