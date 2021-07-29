package com.example.localuser.retrofittest.SVGATest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import org.jetbrains.annotations.NotNull;

public class SVGATestActivity extends AppCompatActivity {
    private SVGAImageView mSVGAImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_svga_test_activity_main);
        mSVGAImage = findViewById(R.id.mSVGAKninghtood);
//        mSVGAImage.setFillMode(SVGAImageView.FillMode.Backward);
        //false表示动画停在最后一帧
        mSVGAImage.setClearsAfterStop(false);
        //设置动画循环播放，-1表示无限循环,0也是无限循环，1表示总共播放一遍，3表示总共播放三遍
        mSVGAImage.setLoops(0);
        initSVGAImageView();
        testSVGA4();
//        HttpResponseCache.install(new File(HnUiUtils.getContext().getExternalFilesDir(null), "svga"), 1024 * 1024 * 128);
    }

    private void initSVGAImageView()
    {
        mSVGAImage.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
                //动画暂停
            }

            @Override
            public void onFinished() {
                //动画结束
            }

            @Override
            public void onRepeat() {
                //重复播放
            }

            @Override
            public void onStep(int i, double v) {
                //动画步骤
            }
        });
    }

    /**
     * 最简单的测试，把一个SVGA动画加载出来
     */
    private void testSVGA()
    {
        new SVGAParser(this).decodeFromAssets("iron.svga", new SVGAParser.ParseCompletion() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (mSVGAImage != null) {
                    mSVGAImage.setVideoItem(videoItem);
                    mSVGAImage.stepToFrame(0, true);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void testSVGA2()
    {
        new SVGAParser(this).decodeFromAssets("posche.svga", new SVGAParser.ParseCompletion() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (mSVGAImage != null) {
                    SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                    dynamicEntity.setHidden(true, "03");//此处psd_17就是要隐藏的元素
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    mSVGAImage.setImageDrawable(drawable);
                    if (mSVGAImage != null) {
                        mSVGAImage.stepToFrame(0, true);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void testSVGA3()
    {
        new SVGAParser(this).decodeFromAssets("posche.svga", new SVGAParser.ParseCompletion() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (mSVGAImage != null) {
                    SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                    dynamicEntity.setHidden(true, "03");//此处psd_17就是要隐藏的元素
                    TextPaint textPaint = new TextPaint();
                    textPaint.setColor(Color.BLUE);//字体颜色
                    textPaint.setTextSize(AppUtils.sp2px(SVGATestActivity.this,16));//字体大小
//                    textPaint.setShadowLayer(3, 2, 2, 0xff000000);//字体阴影，不需要可以不用设置
                    dynamicEntity.setDynamicText("user name", textPaint, "03");
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    mSVGAImage.setImageDrawable(drawable);
                    if (mSVGAImage != null) {
                        mSVGAImage.stepToFrame(0, true);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void testSVGA4()
    {
        new SVGAParser(this).decodeFromAssets("top3.svga", new SVGAParser.ParseCompletion() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (mSVGAImage != null) {
                    if (mSVGAImage != null) {
                        mSVGAImage.setVideoItem(videoItem);
                        mSVGAImage.stepToFrame(0, true);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
