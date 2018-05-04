package com.example.localuser.retrofittest.PullRefreshListView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/28.
 */

public class MyHeadView extends LinearLayout {
    private String TAG = PullRefreshActivity.TAG_PREFIX + getClass().getSimpleName();

    private static float MAX_SCALE = 1.5f;
    private static float INIT_SCALE = 1.0f;
    private static int SCALE_ANIM_PERIOD = 1200;
    //下拉到最大距离后开始加载动画，目前就是旋转
    private static int ROTATE_ANIM_PERIOD = 1500;
    private Context mContext;
    private ViewGroup rootView;
    private ImageView mImageView;
    private TextView mTextView;
    private float mScale = INIT_SCALE;
    private Animation scaleAnimation;
    private RotateAnimation rotateAnimation;
    private AnimationSet mAnimationSet;
    private AnimEndCallback mAnimEndCallback;

    public MyHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void init()
    {
        rootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.layout_pullrefresh_head,this);
        mImageView = rootView.findViewById(R.id.image_view);
        mTextView = rootView.findViewById(R.id.refresh_text_view);
        initAnim();
    }

    public void initAnim()
    {
        scaleAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                mScale = mScale - (mScale - INIT_SCALE)*interpolatedTime;
                Log.d(TAG,"interpolatedTime = "+interpolatedTime+",mscale = "+mScale);
                invalidate();
            }
        };
        scaleAnimation.setDuration(SCALE_ANIM_PERIOD);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mScale = INIT_SCALE;
                //mImageView.startAnimation(rotateAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setDuration(ROTATE_ANIM_PERIOD);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(mAnimEndCallback != null)
                {
                    mAnimEndCallback.animEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAnimationSet = new AnimationSet(true);
        mAnimationSet.setInterpolator(new LinearInterpolator());
        mAnimationSet.addAnimation(scaleAnimation);
        mAnimationSet.addAnimation(rotateAnimation);
    }

    public void scale(float offset)
    {
        float scale = offset/getHeight();
        if(scale <= MAX_SCALE) {
            mScale = scale;
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG,"dispatchDraw(Canvas canvas)");
        Matrix matrix = new Matrix();
        matrix.setScale(mScale,mScale,getWidth()/2,getHeight()/2);
        canvas.setMatrix(matrix);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG,"draw()");
        super.draw(canvas);
    }

    public ImageView getImageView()
    {

        return mImageView;
    }

    public TextView getTextView()
    {
        return mTextView;
    }

    public void startLoadAnimation()
    {
        mImageView.startAnimation(mAnimationSet);
    }

    public void clearAnim()
    {
        mImageView.clearAnimation();
        mScale = INIT_SCALE;
    }

    public interface AnimEndCallback
    {
        void animEnd();
    }

    public void setAnimEndCallback(AnimEndCallback animEndCallback)
    {
        mAnimEndCallback = animEndCallback;
    }
}
