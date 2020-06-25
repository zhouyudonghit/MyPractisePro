package com.example.localuser.retrofittest.Drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.MyApplication;
import com.example.localuser.retrofittest.R;

public class MyDrawable1 extends Drawable {
    private String TAG = LogConfigs.TAG_PREFIX_DRAWABLE_TEST + getClass().getSimpleName();

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.setDensity(2);
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.ic_launcher);
        Log.d(TAG,"bitmap.getHeight() = "+bitmap.getHeight()+",bitmap.getWidth() = " + bitmap.getWidth());
        Log.d(TAG,"bitmap.getScaledHeight = "+bitmap.getScaledHeight(canvas)+",bitmap.getWidth() = " + bitmap.getScaledWidth(canvas));
        Log.d(TAG,"system density = " + MyApplication.getInstance().getResources().getDisplayMetrics().density);
        canvas.drawBitmap(bitmap,0,0,new Paint());
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public int getMinimumHeight() {
        return 30;
    }
}
