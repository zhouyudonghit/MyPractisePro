package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.Utils.AppUtils;

public class RoundRectImageView extends android.support.v7.widget.AppCompatImageView {
    private String TAG = LogConfigs.TAG_PREFIX_CANVAS + getClass().getSimpleName();
    private static final int COLORDRAWABLE_DIMENSION = 1;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private Paint mPaint;
    private Bitmap mBitmap;

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        fitXYTest(canvas);
        fitCenterTest(canvas);
    }

    private void fitXYTest(Canvas canvas)
    {
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "width = " + width);
        mBitmap = getMyFitXYBitmap((BitmapDrawable) getDrawable());
        setup();
        canvas.drawRoundRect(0, 0, width, height, AppUtils.dp2px(8), AppUtils.dp2px(8), mPaint);
    }

    private void fitCenterTest(Canvas canvas)
    {
        int width = getWidth();
        int height = getHeight();
        Log.d(TAG, "width = " + width);
        //方案1，需要处理居中显示问题
        mBitmap = getFitCenterBitMap((BitmapDrawable) getDrawable());
//        setup();
        Log.d(TAG,"mBitmap.getWidth() = "+mBitmap.getWidth());
//        if(mBitmap.getWidth() < width)
//        {
////            canvas.save();
////            Matrix matrix = new Matrix();
////            matrix.setTranslate((width-mBitmap.getWidth())/2.0f,0);
////            canvas.setMatrix(matrix);
//////            canvas.drawRoundRect((width-mBitmap.getWidth())/2.0f,0,(width+mBitmap.getWidth())/2.0f,height,AppUtils.dp2px(8), AppUtils.dp2px(8),mPaint);
//////            canvas.drawRoundRect(0, 0, mBitmap.getWidth(), height, AppUtils.dp2px(8), AppUtils.dp2px(8), mPaint);
////            canvas.restore();
//        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private void setup()
    {
        Shader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        mPaint.setAntiAlias(true);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private Bitmap getMyFitXYBitmap(BitmapDrawable drawable)
    {
        try {
            Bitmap bitmap;
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            bitmap = Bitmap.createBitmap((int) (viewWidth), (int) (viewHeight), BITMAP_CONFIG);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private Bitmap getFitCenterBitMap(BitmapDrawable drawable)
    {
        try {
            Bitmap bitmap;

            Bitmap source = drawable.getBitmap();
            Log.d(TAG,"drawable.getBounds() = "+drawable.getBounds());
            int sourceWidth = source.getWidth();
            int sourceHeight = source.getHeight();
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            float ratio = 0;
            float widthRatio = viewWidth*1.0f/sourceWidth;
            if(widthRatio*sourceHeight <= viewHeight)
            {
                ratio = widthRatio;
            }else{
                ratio = viewHeight*1.0f/sourceHeight;
            }
            bitmap = Bitmap.createBitmap((int) (sourceWidth*ratio), (int) (sourceHeight*ratio), BITMAP_CONFIG);
            Log.d(TAG,"ratio = "+ratio);

            Canvas canvas = new Canvas(bitmap);
            //方案1，只处理bitmap，居中留在view绘制的时候处理
//            Log.d(TAG,"canvas.getWidth() = "+canvas.getWidth()+",canvas.getHeight() = "+canvas.getHeight());
            Log.d(TAG,"drawable.getBounds() = "+drawable.getBounds());
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
            //方案2，直接给出一张带有居中效果的Bitmap，view不用处理居中效果了
            //方案3，不给bitmap，直接在原bitmap上利用先平移再缩放的策略直接处理，这个可能是imageview的方案
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
