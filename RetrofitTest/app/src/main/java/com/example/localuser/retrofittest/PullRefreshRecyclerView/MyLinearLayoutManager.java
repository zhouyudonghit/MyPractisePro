package com.example.localuser.retrofittest.PullRefreshRecyclerView;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

public class MyLinearLayoutManager extends LinearLayoutManager {
    private String TAG = PullRefreshRecyclerViewActivity.TAG_PREX + getClass().getSimpleName();
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    /**
     * 下面的实现的效果，必须RecycleView主动调用其smoothScrollToPosition（int position）方法才有效果，而不是RecycleView滑动时自动调用的。
     * @param recyclerView
     * @param state
     * @param position
     */
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()){
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                Log.d(TAG,"calculateSpeedPerPixel");
                return 200.0F / (float)displayMetrics.densityDpi;
            }

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                Log.d(TAG,"computeScrollVectorForPosition");
                return super.computeScrollVectorForPosition(targetPosition);
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
