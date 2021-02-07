package com.example.localuser.retrofittest.MatrixTest;

import android.graphics.Matrix;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

/**
 * 参看链接：https://zhuanlan.zhihu.com/p/69068199?from_voters_page=true
 */
public class MatrixTest {
    private String TAG = LogConfigs.TAG_PREFIX_MATRIX_TEST + getClass().getSimpleName();
    private Matrix matrix = new Matrix();

    public void test()
    {
        testTranslate();
    }

    private void testScale()
    {
        Log.d(TAG,"matrix = "+matrix);
        matrix.setScale(0.4f,0.4f);
        //Matrix{[0.4, 0.0, 0.0][0.0, 0.4, 0.0][0.0, 0.0, 1.0]}
        Log.d(TAG,"matrix = "+matrix);

        matrix.setScale(0.6f,0.6f);
        //Matrix{[0.6, 0.0, 0.0][0.0, 0.6, 0.0][0.0, 0.0, 1.0]}
        //多次调用，好像只最后一次的生效
        Log.d(TAG,"matrix = "+matrix);
    }

    private void testScale1()
    {
        Log.d(TAG,"matrix = "+matrix);
        matrix.setScale(0.4f,0.4f,300,300);
        //Matrix{[0.4, 0.0, 180.0][0.0, 0.4, 180.0][0.0, 0.0, 1.0]}
        Log.d(TAG,"matrix = "+matrix);

        matrix.setScale(0.6f,0.6f,300,300);
        //Matrix{[0.4, 0.0, 180.0][0.0, 0.4, 180.0][0.0, 0.0, 1.0]}
        //也是只最后一次生效
        Log.d(TAG,"matrix = "+matrix);
    }

    private void testTranslate()
    {
        Log.d(TAG,"matrix = "+matrix);
        matrix.setTranslate(30,30);
        Log.d(TAG,"matrix = "+matrix);
    }

    private void testRotate()
    {
        Log.d(TAG,"matrix = "+matrix);
        matrix.setRotate(30);
        Log.d(TAG,"matrix = "+matrix);
    }

    private void testSkew()
    {
        Log.d(TAG,"matrix = "+matrix);
        matrix.setSkew(0.3f,0.3f);
        Log.d(TAG,"matrix = "+matrix);
    }
}
