package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.localuser.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by localuser on 2018/4/24.
 */

public class MyPullRefreshRecyclerView extends LinearLayout implements MyHeadView.AnimEndCallback{
    private String TAG = PullRefreshRecyclerViewActivity.TAG_PREX + getClass().getSimpleName();
    private static int MOVE_DIRECTION_UP = 0;
    private static int MOVE_DIRECTION_DOWN = 1;
    //加载动画执行完后，回滚的周期
    private static int SCROLL_BACK_UP_TIME = 500;
    //没有下来到最大距离，快速回滚的时间
    private static int SCROLL_BACK_UP_SHORT_TIME = 200;
    //一个比例参数，使得手指可以滑动更长的距离
    private static float OFFSET_RATE = 0.5f;

    private Context mContext;
    private RecyclerView mListView;
    private LinearLayout rootView,realRootView;
    private MyHeadView headerView,footView;
    private ImageView mImageView;
    private Point mOldPoint,mNewPint,mStartPoint;
    private Scroller mScroller;
    private int itemCount = 17;
    private boolean scrolled = false;
    private int mMoveDirection;
    private int totalOffset = 0;
    private RotateAnimation rotateAnimation;
    private boolean headAnimRunning = false;
    private boolean footAnimRunning = false;

    private MyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> mDatas;
    private int dividerHeight = 0;

    public MyPullRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void init()
    {
        Log.d(TAG,"init()");
        rootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_pullrefresh_recyclerview,this);
        realRootView = rootView.findViewById(R.id.root_layout);
        headerView = rootView.findViewById(R.id.head_layout);
        headerView.setAnimEndCallback(this);
        //headerView.setVisibility(View.GONE);

        footView = rootView.findViewById(R.id.foot_layout);
        footView.setVisibility(View.VISIBLE);
        footView.getTextView().setText("上拉加载更多");
        footView.setAnimEndCallback(this);

        mListView = rootView.findViewById(R.id.my_listview);
        linearLayoutManager = new MyLinearLayoutManager(mContext);
        mListView.setLayoutManager(linearLayoutManager);
        mListView.setItemAnimator(new DefaultItemAnimator());
        mDatas = new ArrayList<>();
        adapter = new MyAdapter(mContext,mDatas);
        mListView.setAdapter(adapter);
        setdata();
        DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        MyDividerDrawable dividerDarwble = new MyDividerDrawable();
        divider.setDrawable(dividerDarwble);
        mListView.addItemDecoration(divider);
        dividerHeight = dividerDarwble.getIntrinsicHeight();
        mOldPoint = new Point();
        mNewPint = new Point();
        mStartPoint = new Point();
        mScroller = new Scroller(mContext);
        mListView.smoothScrollToPosition(20);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG,"dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw");
        super.draw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG,"onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //额外加一个headview的高度，是为了隐藏headview之后，listview的高度能占满父控件
        //目前只针对竖屏有效，横屏还需要修改
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure()");
//        headerView.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(270,MeasureSpec.getMode(heightMeasureSpec)));
        //一定要设置好listview的高度，高度和父控件的高度一样，这样才没有问题
        Log.d(TAG,"getMeasuredHeight() = "+getMeasuredHeight());
        Log.d(TAG,"mListView.getMeasuredHeight() = "+mListView.getMeasuredHeight());
        if(getMeasuredHeight() != 0)
        {
            int listHeight = mListView.getMeasuredHeight();
            if(listHeight < getMeasuredHeight())
            {
                //mListView.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(listHeight,MeasureSpec.EXACTLY));
            }else{
                mListView.measure(widthMeasureSpec,heightMeasureSpec);
            }
        }
        footView.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(headerView.getMeasuredHeight(),MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int height = headerView.getMeasuredHeight();
        //自身的左上角和右下角
        super.onLayout(changed,l,t,r,b);
        Log.d(TAG,"onLayout,height="+height);
        Log.d(TAG,"footview.height = "+footView.getHeight());
        Log.d(TAG,"listview.height = "+mListView.getHeight());
        if(changed && !scrolled) {
            realRootView.scrollBy(0, height);
            scrolled = true;
        }
        //mListView.layout(l,t,r,b);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent"+ev.toString());
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                onDispatchTouchEventDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                onDispatchTouchEventMove(ev);
                break;
            case MotionEvent.ACTION_UP:
//                //onDispatchTouchEventUp(ev);
//                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void onDispatchTouchEventDown(MotionEvent ev)
    {
        mOldPoint.set((int)ev.getRawX(),(int)ev.getRawY());
        mStartPoint.set(mOldPoint.x,mOldPoint.y);
        totalOffset = 0;
    }

    public void onDispatchTouchEventMove(MotionEvent ev)
    {
        mNewPint.set((int)ev.getRawX(),(int)ev.getRawY());
        if(headAnimRunning) {
            if(mNewPint.y - mStartPoint.y < 0)
            {
                headerView.clearAnim();
                headAnimRunning = false;
                mScroller.startScroll(0,0,0,headerView.getHeight(),SCROLL_BACK_UP_TIME);
                invalidate();
            }
        }
        if(footAnimRunning)
        {
            if(mNewPint.y - mStartPoint.y > 0)
            {
                footView.clearAnim();
                footAnimRunning = false;
                mScroller.startScroll(0,0,0,footView.getHeight(),SCROLL_BACK_UP_TIME);
                invalidate();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent"+ev.toString());
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                return onInterceptTouchEventMove(ev);
            default:
                return super.onInterceptTouchEvent(ev);
        }
    }

    public boolean onInterceptTouchEventMove(MotionEvent ev)
    {
        Log.d(TAG,"onInterceptTouchEventMove(),ev.getRawY()="+ev.getRawY()+"mOldPoint.y="+mOldPoint.y);
        boolean isIntercepted = false;
        if(isListviewTop())
        {
            if((int)ev.getRawY() > mOldPoint.y)
            {
                isIntercepted = true;
                mMoveDirection = MOVE_DIRECTION_DOWN;
                if(headAnimRunning)
                {
                    headerView.clearAnim();
                    headAnimRunning = false;
                    totalOffset = headerView.getHeight();
                }
            }
        }
        if(isListviewBottom())
        {
            if((int)ev.getRawY() < mOldPoint.y)
            {
                isIntercepted = true;
                mMoveDirection = MOVE_DIRECTION_UP;
                //footView.setVisibility(View.VISIBLE);
                if(footAnimRunning)
                {
                    footView.clearAnim();
                    footAnimRunning = false;
                    totalOffset = footView.getHeight();
                }
            }
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent"+event.toString());
        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                onTouchEventMove(event);
                return true;
            case MotionEvent.ACTION_UP:
                onTouchEventUp(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    public RecyclerView getListView()
    {
        return mListView;
    }

    public void onTouchEventMove(MotionEvent ev)
    {
        Log.d(TAG, "onTouchEventMove()");
        mNewPint.set((int) ev.getRawX(), (int) ev.getRawY());
        int offset = (int) ((mOldPoint.y - mNewPint.y) * OFFSET_RATE);
        int footHeight = footView.getHeight();
        int headHeight = headerView.getHeight();
        if(mMoveDirection == MOVE_DIRECTION_DOWN) {
            //这一步是保证totalOffset+offset不超过headHeight
            if(totalOffset < headHeight && totalOffset - offset > headHeight)
            {
                offset = totalOffset - headHeight;
            }
            //这一步是保证此次获取到的间隔不大于headHeight，在手指滑动很快的时候，第一次获取到的offset的值有可能大于headHeight
            if(Math.abs(offset) > headHeight)
            {
                offset = -headHeight;
            }
            totalOffset += -offset;
            Log.d(TAG, "onTouchEventMove(),offset = " + offset+",totaloffset = "+totalOffset);
            Log.d(TAG,"head.gettop="+headerView.getTop());
            if(realRootView.getScrollY() > 0) {
                if (totalOffset <= headerView.getHeight()) {
                    realRootView.scrollBy(0, offset);
                } else {
                    headerView.scale(totalOffset);
                }
            }else{
                headerView.scale(totalOffset);
            }
        }else{
            Log.d(TAG,"offset = "+offset+",realrootview.getScrollY()="+realRootView.getScrollY());
            Log.d(TAG,"footView.getHeight() = "+footView.getHeight());
            if(totalOffset < footHeight && totalOffset - offset > footHeight)
            {
                offset = footHeight - totalOffset;
            }
            if(offset > footHeight)
            {
                offset = footHeight;
            }
            totalOffset += offset;
            if(realRootView.getScrollY() < headHeight+footHeight)
            {
                if(totalOffset <= footHeight)
                {
                    realRootView.scrollBy(0,offset);
                }else{
                    footView.scale(totalOffset);
                }
            }else{
                footView.scale(totalOffset);
            }
            //realRootView.scrollBy(0,offset);
            //scrollBy(0,offset);
        }
        mOldPoint.set(mNewPint.x, mNewPint.y);
    }

    public void onTouchEventUp(MotionEvent ev)
    {
        mNewPint.set((int)ev.getRawX(),(int)ev.getRawY());
        totalOffset = (int) ((mNewPint.y-mStartPoint.y)*OFFSET_RATE);
        Log.d(TAG,"totaloffset = "+totalOffset+",headerView.getHeight()="+headerView.getHeight()+",rootView.getScrollY()="+rootView.getScrollY());
        if(mMoveDirection == MOVE_DIRECTION_DOWN) {
            if(totalOffset >= headerView.getHeight()) {
                Log.d(TAG,"startanimation()");
                headAnimRunning = true;
                headerView.startLoadAnimation();
            }else{
//                mScroller.startScroll(0, rootView.getScrollY(), 0, Math.min(totalOffset,headerView.getHeight()-rootView.getScrollY()), SCROLL_BACK_UP_SHORT_TIME);
                mScroller.startScroll(0, rootView.getScrollY(), 0, headerView.getHeight()-rootView.getScrollY(), SCROLL_BACK_UP_SHORT_TIME);
                invalidate();
            }
        }else{
            totalOffset = totalOffset*-1;
            Log.d(TAG,"totalOffset = "+totalOffset+",footView.getHeight()"+footView.getHeight());
            if(totalOffset >= footView.getHeight())
            {
                footAnimRunning = true;
                footView.startLoadAnimation();
            }else{
                mScroller.startScroll(0, realRootView.getScrollY(), 0, footView.getHeight()-realRootView.getScrollY(), SCROLL_BACK_UP_SHORT_TIME);
                invalidate();
            }
        }
    }

    @Override
    public void computeScroll() {
        Log.d(TAG,"computeScroll()");
        super.computeScroll();
        if(mScroller.computeScrollOffset())
        {
            if(mMoveDirection == MOVE_DIRECTION_DOWN) {
                Log.d(TAG, "rootView.getScrollY()" + rootView.getScrollY() + ",mScroller.getCurrY()" + mScroller.getCurrY());
                realRootView.scrollTo(0, mScroller.getCurrY());
            }else{
                Log.d(TAG, "realrootView.getScrollY()" + realRootView.getScrollY() + ",mScroller.getCurrY()" + mScroller.getCurrY());
                realRootView.scrollTo(0, mScroller.getCurrY());
            }
            invalidate();
        }
    }

    @Override
    public void animEnd() {
        if(mMoveDirection == MOVE_DIRECTION_DOWN) {
            Log.d(TAG, "onAnimationEnd,headerView.getHeight()=" + headerView.getHeight());
            headAnimRunning = false;
            mScroller.startScroll(0, 0, 0, headerView.getHeight(), SCROLL_BACK_UP_TIME);
        }else{
            Log.d(TAG, "onAnimationEnd,headerView.getHeight()=" + footView.getHeight());
            footAnimRunning = false;
            mScroller.startScroll(0, headerView.getHeight()+footView.getHeight(), 0, -footView.getHeight(), SCROLL_BACK_UP_TIME);
        }
        totalOffset = 0;
        invalidate();
    }

    /**
     * 判断ListView是否滚动到顶部
     * @return
     */
    public boolean isListviewTop()
    {
        Log.d(TAG,"isListviewTop()");
        boolean isTop = false;
        if(mListView != null)
        {
            Log.d(TAG,"mListView.getFirstVisiblePosition() = "+linearLayoutManager.findFirstVisibleItemPosition());
            if(linearLayoutManager.findFirstVisibleItemPosition() == 0)
            {
                View tmpView = mListView.getChildAt(0);
                Log.d(TAG,"tmpView.getTop() = "+tmpView.getTop());
                if(tmpView != null && tmpView.getTop() == 0)
                {
                    isTop = true;
                }
            }
        }
        return isTop;
    }

    public boolean isListviewBottom()
    {
        Log.d(TAG,"isListviewBottom()");
        boolean isBottom = false;
        if(mListView != null)
        {
            Log.d(TAG,"mListView.getLastVisiblePosition()="+linearLayoutManager.findLastVisibleItemPosition()+",mListView.getChildCount()="+mListView.getChildCount());
            if(linearLayoutManager.findLastVisibleItemPosition() == itemCount-1)
            {
                View bottomView = mListView.getChildAt(mListView.getChildCount()-1);
                Log.d(TAG,"bottomView.getBottom() = "+bottomView.getBottom()+",mListView.getHeight()="+mListView.getHeight());
                if(bottomView != null && (bottomView.getBottom()+dividerHeight) == mListView.getHeight())
                {
                    isBottom = true;
                }
            }
        }
        return isBottom;
    }

    public void setdata()
    {
        mDatas.add("item 0");
        mDatas.add("item 1");
        mDatas.add("item 2");
        mDatas.add("item 3");
        mDatas.add("item 4");
        mDatas.add("item 5");
        mDatas.add("item 6");
        mDatas.add("item 7");
        mDatas.add("item 8");
        mDatas.add("item 9");
        mDatas.add("item 10");
        mDatas.add("item 11");
        mDatas.add("item 12");
        mDatas.add("item 13");
        mDatas.add("item 14");
        mDatas.add("item 15");
        mDatas.add("item 16");
        mDatas.add("item 17");
        mDatas.add("item 18");
        mDatas.add("item 19");
        mDatas.add("item 20");
        adapter.setDatas(mDatas);
        itemCount = mDatas.size();
    }
}