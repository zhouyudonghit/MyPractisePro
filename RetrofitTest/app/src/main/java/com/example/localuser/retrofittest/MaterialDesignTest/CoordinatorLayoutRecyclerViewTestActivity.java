package com.example.localuser.retrofittest.MaterialDesignTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.ListViewTest.recyclelistview.BaseCommonRecycleAdapter;
import com.example.localuser.retrofittest.ListViewTest.recyclelistview.CommonRecycleViewHolder;
import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;

public class CoordinatorLayoutRecyclerViewTestActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private BaseCommonRecycleAdapter mBaseCommonRecycleAdapter;
    private AppBarLayout mAppBarLayout;
    private ViewPager mViewPager;
    private List<String> mDatas;
    private Toolbar mToolbar;
    private AppBarLayout.ScrollingViewBehavior behavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        initViews();
    }

    private void initViews()
    {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("hahaha");
        setSupportActionBar(mToolbar);
        mAppBarLayout = findViewById(R.id.appbar);
        setScrollFlag();
        initRecyclerView(true);
    }

    private void initRecyclerView(boolean show)
    {
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        myRecycleAdapter = new MyRecycleAdapter<>(mDatas,this);
        initRecyclerViewDatas();
        mBaseCommonRecycleAdapter = new BaseCommonRecycleAdapter<String>(R.layout.layout_dslv_item,mDatas,this) {
            @Override
            public void convertView(CommonRecycleViewHolder commonRecycleViewHolder, String data, int position) {
                TextView dataView = commonRecycleViewHolder.getViewById(R.id.data_tv);
                dataView.setText(data);
            }
        };
        mRecycleView.setAdapter(mBaseCommonRecycleAdapter);
        if(show)
        {
            mRecycleView.setVisibility(View.VISIBLE);
        }else{
            mRecycleView.setVisibility(View.GONE);
        }
    }

    private void initRecyclerViewDatas()
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

    /**
     * scroll:The view will be scroll in direct relation to scroll events.
     * This flag needs to be set for any of the other flags to take effect.
     * If any sibling views before this one do not have this flag, then this value has no effect.
     *自己的翻译：view会根据滑动事件进行滑动。若要其他任何的flag想要起作用，这个flag都必须设置。如果这个view之前的兄弟view没有设置该flag，那么该view的设置也不会有作用。
     *
     * enterAlways:When entering (scrolling on screen) the view will scroll on any downwards scroll event, regardless of whether the scrolling view is also scrolling.
     * This is commonly referred to as the 'quick return' pattern.
     *自己的翻译：当滑动屏幕的时候，view会根据任何的下滑事件进行滑动，而不用考虑即将滑动的view是否也即将进行滑动，经常被用到快速返回模式。比如该view会优先下拉，然后listview执行滑动。
     * 如果不设置该模式，那么listview会优先执行下滑操作，直到滑到第一个数据为止，然后该view执行下滑操作。
     *
     * enterAlwaysCollapsed：An additional flag for 'enterAlways' which modifies the returning view to only initially scroll back to it's collapsed height.
     * Once the scrolling view has reached the end of it's scroll range, the remainder of this view will be scrolled into view.
     * The collapsed height is defined by the view's minimum height.
     *自己的翻译：enterAlways的附加值。这里涉及到Child View的高度和最小高度，向下滚动时，Child View先向下滚动最小高度值，然后Scrolling View开始滚动，到达边界时，Child View再向下滚动，直至显示完全。
     *android:layout_height="@dimen/dp_200"
     * android:minHeight="@dimen/dp_56"
     * ...
     * app:layout_scrollFlags="scroll|enterAlways|enterAlwaysCollapsed"
     *
     * exitUntilCollapsed：When exiting (scrolling off screen) the view will be scrolled until it is 'collapsed'.
     * The collapsed height is defined by the view's minimum height.
     *自己的翻译：这里也涉及到最小高度。发生向上滚动事件时，Child View向上滚动退出直至最小高度，然后Scrolling View开始滚动。也就是，上滑的时候，Child View不会完全退出屏幕，而是退出到最小高度就不动了。
     * 下滑的效果如同enterAlwaysCollapsed
     * android:layout_height="@dimen/dp_200"
     * android:minHeight="@dimen/dp_56"
     * ...
     * app:layout_scrollFlags="scroll|exitUntilCollapsed"
     *
     *snap：Upon a scroll ending, if the view is only partially visible then it will be snapped and scrolled to it's closest edge.
     * For example, if the view only has it's bottom 25% displayed, it will be scrolled off screen completely.
     * Conversely, if it's bottom 75% is visible then it will be scrolled fully into view.
     *简单理解，就是Child View滚动比例的一个吸附效果。也就是说，Child View不会存在局部显示的情况，滚动Child View的部分高度，当我们松开手指时，Child View要么向上全部滚出屏幕，要么向下全部滚进屏幕，有点类似ViewPager的左右滑动。
     *
     *
     *
     */
    private void setScrollFlag()
    {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
//        params.setScrollFlags(SCROLL_FLAG_SCROLL|SCROLL_FLAG_SNAP);
        params.setScrollFlags(SCROLL_FLAG_SCROLL|SCROLL_FLAG_ENTER_ALWAYS);
    }
}
