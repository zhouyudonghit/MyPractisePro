package com.example.localuser.retrofittest.ListViewTest.recyclelistview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.localuser.retrofittest.Configs.LogConfigs;

/**
 * https://blog.csdn.net/shangmingchao/article/details/51456319
 * https://www.jianshu.com/p/41ae13016243
 */
public class ItemDecor extends RecyclerView.ItemDecoration {
    private String TAG = LogConfigs.TAG_PREFIX_LISTVIEW + getClass().getSimpleName();
    Paint mPaint;

    public ItemDecor() {
        mPaint = new Paint();
        mPaint.setColor(0x99FF0000);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + 50;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        Log.d(TAG,"itemPosition = "+itemPosition);
        if(itemPosition != 0)
        {
            outRect.set(0, 50, 0, 0);
        }
    }
}
