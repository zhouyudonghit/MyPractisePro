package com.example.localuser.retrofittest.PullRefreshListView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/24.
 */

public class PullRefreshListView extends LinearLayout implements MyHeadView.AnimEndCallback{
    private String TAG = PullRefreshActivity.TAG_PREFIX + getClass().getSimpleName();

    private static int MOVE_DIRECTION_UP = 0;
    private static int MOVE_DIRECTION_DOWN = 1;
    //加载动画执行完后，回滚的周期
    private static int SCROLL_BACK_UP_TIME = 1000;
    //没有下来到最大距离，快速回滚的时间
    private static int SCROLL_BACK_UP_SHORT_TIME = 400;
    //一个比例参数，使得手指可以滑动更长的距离
    private static float OFFSET_RATE = 0.5f;

    private Context mContext;
    private ListView mListView;
    private LinearLayout rootView;
    private MyHeadView headerView,footView;
    private ImageView mImageView;
    private Point mOldPoint,mNewPint,mStartPoint;
    private Scroller mScroller;
    private int itemCount = 30;
    private boolean scrolled = false;
    private int mMoveDirection;
    private int totalOffset = 0;
    private RotateAnimation rotateAnimation;
    private boolean animRunning = false;

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void init()
    {
        Log.d(TAG,"init()");
        rootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_pullrefresh_listview,this);
        headerView = rootView.findViewById(R.id.head_layout);
        headerView.setAnimEndCallback(this);
        //headerView.setVisibility(View.GONE);

        footView = rootView.findViewById(R.id.foot_layout);
        footView.setVisibility(View.GONE);

        mListView = rootView.findViewById(R.id.my_listview);
        mOldPoint = new Point();
        mNewPint = new Point();
        mStartPoint = new Point();
        mScroller = new Scroller(mContext);
        initAnim();
    }

    public void initAnim()
    {

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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure,getChildCount()="+getChildCount());
        Log.d(TAG,"onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int height = headerView.getMeasuredHeight();
        super.onLayout(changed,l,t,r,b);
        Log.d(TAG,"onLayout,height="+height);
        if(changed && !scrolled)
        {
            scrollBy(0,height);
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
    }

    public void onDispatchTouchEventMove(MotionEvent ev)
    {
        if(animRunning) {
            mNewPint.set((int)ev.getRawX(),(int)ev.getRawY());
            if(mNewPint.y - mStartPoint.y < 0)
            {
                headerView.clearAnimation();
                animRunning = false;
                mScroller.startScroll(0,0,0,headerView.getHeight(),SCROLL_BACK_UP_TIME);
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
                if(animRunning)
                {
                    headerView.clearAnim();
                    animRunning = false;
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
                footView.setVisibility(View.VISIBLE);
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

    public ListView getListView()
    {
        return mListView;
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
            Log.d(TAG,"mListView.getFirstVisiblePosition() = "+mListView.getFirstVisiblePosition());
            if(mListView.getFirstVisiblePosition() == 0)
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
            Log.d(TAG,"mListView.getLastVisiblePosition()="+mListView.getLastVisiblePosition()+",mListView.getChildCount()="+mListView.getChildCount());
            if(mListView.getLastVisiblePosition() == itemCount-1)
            {
                View bottomView = mListView.getChildAt(mListView.getChildCount()-1);
                Log.d(TAG,"bottomView.getBottom() = "+bottomView.getBottom()+",mListView.getHeight()="+mListView.getHeight());
                if(bottomView != null && bottomView.getBottom() == mListView.getHeight())
                {
                    isBottom = true;
                }
            }
        }
        return isBottom;
    }

    public void onTouchEventMove(MotionEvent ev)
    {
        mNewPint.set((int) ev.getRawX(), (int) ev.getRawY());
        int offset = (int) ((mOldPoint.y - mNewPint.y) * OFFSET_RATE);
        if(mMoveDirection == MOVE_DIRECTION_DOWN) {
            if(totalOffset < headerView.getHeight() && totalOffset - offset > headerView.getHeight())
            {
                offset = totalOffset - headerView.getHeight();
            }
            totalOffset += -offset;
            Log.d(TAG, "onTouchEventMove(),offset = " + offset+",totaloffset = "+totalOffset);
            Log.d(TAG,"head.gettop="+headerView.getTop()+",getScrollY()="+getScrollY());
            if(getScrollY() > 0) {
                if (totalOffset <= headerView.getHeight()) {
                    scrollBy(0, offset);
                } else {
                    headerView.scale(totalOffset);
                }
            }else{
                headerView.scale(totalOffset);
            }
            mOldPoint.set(mNewPint.x, mNewPint.y);
        }else{

        }
    }

    public void onTouchEventUp(MotionEvent ev)
    {
        mNewPint.set((int)ev.getRawX(),(int)ev.getRawY());
        totalOffset = (int) ((mNewPint.y-mStartPoint.y)*OFFSET_RATE);
        Log.d(TAG,"totaloffset = "+totalOffset+",headerView.getHeight()="+headerView.getHeight()+",rootView.getScrollY()="+rootView.getScrollY());
        if(mMoveDirection == MOVE_DIRECTION_DOWN) {
            if(totalOffset >= headerView.getHeight()) {
                Log.d(TAG,"startanimation()");
                animRunning = true;
                headerView.startLoadAniamtion();
            }else{
//                mScroller.startScroll(0, rootView.getScrollY(), 0, Math.min(totalOffset,headerView.getHeight()-rootView.getScrollY()), SCROLL_BACK_UP_SHORT_TIME);
                mScroller.startScroll(0, rootView.getScrollY(), 0, headerView.getHeight()-rootView.getScrollY(), SCROLL_BACK_UP_SHORT_TIME);
                invalidate();
            }
        }else{

        }
    }

    @Override
    public void computeScroll() {
        Log.d(TAG,"computeScroll()");
        super.computeScroll();
        if(mScroller.computeScrollOffset())
        {
            Log.d(TAG,"rootView.getScrollY()"+rootView.getScrollY()+",mScroller.getCurrY()"+mScroller.getCurrY());
            scrollTo(0,mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void animEnd() {
        Log.d(TAG, "onAnimationEnd,headerView.getHeight()=" + headerView.getHeight());
        animRunning = false;
        totalOffset = 0;
        mScroller.startScroll(0, 0, 0, headerView.getHeight(), SCROLL_BACK_UP_TIME);
        invalidate();
    }
}