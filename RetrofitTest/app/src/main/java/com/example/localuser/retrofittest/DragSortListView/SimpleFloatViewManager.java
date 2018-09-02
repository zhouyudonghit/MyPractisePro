package com.example.localuser.retrofittest.DragSortListView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Simple implementation of the FloatViewManager class. Uses list
 * items as they appear in the ListView to create the floating View.
 */
public class SimpleFloatViewManager implements DragSortListView.FloatViewManager {

    private Bitmap mFloatBitmap;

    private ImageView mImageView;

    private int mFloatBGColor = Color.BLACK;

    private ListView mListView;

    public SimpleFloatViewManager(ListView lv) {
        mListView = lv;
    }

    public void setBackgroundColor(int color) {
        mFloatBGColor = color;
    }

    /**
     * This simple implementation creates a Bitmap copy of the
     * list item currently shown at ListView <code>position</code>.
     */
    @Override
    public View onCreateFloatView(int position) {
        // Guaranteed that this will not be null? I think so. Nope, got
        // a NullPointerException once...
        View v = mListView.getChildAt(position + mListView.getHeaderViewsCount() - mListView.getFirstVisiblePosition());

        if (v == null) {
            return null;
        }

        v.setPressed(false);

        // Create a copy of the drawing cache so that it does not get
        // recycled by the framework when the list tries to clean up memory
        //v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        v.setDrawingCacheEnabled(true);
        mFloatBitmap = Bitmap.createBitmap(v.getDrawingCache());
        mFloatBitmap = scaleBitmap(mFloatBitmap,1.05f);
        v.setDrawingCacheEnabled(false);

        if (mImageView == null) {
            mImageView = new ImageView(mListView.getContext());
        }
        mImageView.setBackgroundColor(mFloatBGColor);
        mImageView.setPadding(0, 0, 0, 0);
        mImageView.setImageBitmap(mFloatBitmap);
        mImageView.setScaleType(ImageView.ScaleType.CENTER);
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[]{Color
                .parseColor("#00000000"),Color.parseColor("#ffffffff"),Color.parseColor("#00000000")});
        drawable.setGradientCenter(0.5f,0.5f);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setSize(v.getWidth(), (int) (v.getHeight() * 1.05f+ 10));
        mImageView.setBackgroundDrawable(drawable);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(v.getWidth(), (int) (v.getHeight() * 1.05f+ 10)));

        return mImageView;
    }

    /**
     * 缩放Bitmap
     * @param bitmap 原图
     * @param ratio 比例
     *
     * @return 处理后的图片
     */
    private Bitmap scaleBitmap(Bitmap bitmap,float ratio) {
        Matrix matrix = new Matrix();
        matrix.postScale(ratio,ratio); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    /**
     * This does nothing
     */
    @Override
    public void onDragFloatView(View floatView, Point position, Point touch) {
        // do nothing
    }

    /**
     * Removes the Bitmap from the ImageView created in
     * onCreateFloatView() and tells the system to recycle it.
     */
    @Override
    public void onDestroyFloatView(View floatView) {
        ((ImageView) floatView).setImageDrawable(null);

        mFloatBitmap.recycle();
        mFloatBitmap = null;
    }

}

