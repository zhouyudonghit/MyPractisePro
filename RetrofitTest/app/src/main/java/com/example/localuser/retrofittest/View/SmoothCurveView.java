package com.example.localuser.retrofittest.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.example.localuser.retrofittest.Utils.DateUtil;
import com.example.localuser.retrofittest.View.bean.WeightInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 平滑曲线图
 */
public class SmoothCurveView extends LineWithShadowView{
    private String TAG = LogConfigs.TAG_PREFIX_MYVIEW + getClass().getSimpleName();
    //二阶贝塞尔曲线
    private final static int CURVE_TYPE_QUTO_AHEAD_X = 0;
    private final static int CURVE_TYPE_QUTO_AHEAD_Y = 1;
    //二阶贝塞尔曲线
    private final static int CURVE_TYPE_QUTO_BEHIND_X = 2;
    private final static int CURVE_TYPE_QUTO_BEHIND_Y = 3;
    //三阶贝塞尔曲线
    private final static int CURVE_TYPE_CUBIC = 4;
    private float mPaddingToTopOrBottom;
    private float mPaddingToLeftOrRight;
    private final static float CONTROL_PERCENT = 1/4.0f;
    private Path mPath;
    private Date mMonthDate;
    private List<Point> mPointLocations = new ArrayList<>();
    private List<WeightInfo> mDatas = new ArrayList<>();
    private float mMaxWeight = -1;
    private float mMinWeight = -1;
    private Paint mOtherPaint;

    public SmoothCurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mOtherPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //两条线的连接处圆角方式，平滑
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaddingToTopOrBottom = AppUtils.dip2px(getContext(),10)*AppUtils.getSreenScaleByWidth(getContext());
        mPaddingToLeftOrRight = AppUtils.dip2px(getContext(),12)*AppUtils.getSreenScaleByWidth(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Path path = new Path();
//        path.moveTo(100,300);
//        float controlX1 = (400-100)*CONTROL_PERCENT + 100;
//        float controlX2 = 400 - (400-100)*CONTROL_PERCENT;
//        Log.d(TAG,"controlX1 = "+controlX1+",controlX2 = "+controlX2);
////        path.cubicTo(controlX1,300,controlX2,100,400,100);
//        path.quadTo(100,300,400,100);
//        canvas.drawPath(path,mPaint);
        drawCurve(canvas);
    }

    private void drawCurve(Canvas canvas)
    {
        if(mDatas == null || mDatas.size() == 0)
        {
            //没有数据，不绘制
            return;
        }
        drawThreeTickMarks(canvas);
        calculatePointLocations();
        constructPath();
        if(mPath != null)
        {
            LinearGradient linearGradient = new LinearGradient(mPointLocations.get(0).x, mPointLocations.get(0).y, mPointLocations.get(1).x, mPointLocations.get(1).y, Color.parseColor("#FEAE31"), Color.parseColor("#FC7616"), Shader.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            canvas.drawPath(mPath, mPaint);
        }
        drawCircles(canvas);
    }

    private void drawThreeTickMarks(Canvas canvas)
    {
        mOtherPaint.setStyle(Paint.Style.STROKE);
        mOtherPaint.setColor(Color.parseColor("#80DCB07D"));
        int lineWidth = AppUtils.dip2px(getContext(),0.5f);
        if(lineWidth < 2)
        {
            lineWidth = 2;
        }
        mOtherPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(0,lineWidth/2,getWidth(),lineWidth/2,mOtherPaint);
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,mOtherPaint);
        canvas.drawLine(0,getHeight() - lineWidth/2,getWidth(),getHeight()-lineWidth/2,mOtherPaint);
    }

    private void calculatePointLocations()
    {
        int days = DateUtil.getMonthDay(mMonthDate);
        float beginX = AppUtils.dip2px(getContext(),12)*AppUtils.getSreenScaleByWidth(getContext());
        float endX = getWidth() - beginX;
        float internalX = (endX - beginX)/(days - 1);

        float beginY = mPaddingToTopOrBottom;
        float endY = getHeight() - mPaddingToTopOrBottom;
        float internalY = (endY - beginY);

        Log.d(TAG,"days = "+days+",beginX = "+beginX + ",endX = "+endX+",internalX = " + internalX);
        Log.d(TAG,"days = "+days+",beginY = "+beginY + ",endY = " + endY + ",internalY = " + internalY);
        for (WeightInfo weightInfo : mDatas) {
            if (weightInfo != null) {
                Point point = new Point();
                point.x = (int) (beginX + internalX * weightInfo.getIndexOfDay());
                if(mMaxWeight == mMinWeight || (mMaxWeight-mMinWeight) < 0.1f)
                {
                    point.y = getHeight()/2;
                }else {
                    point.y = (int) (beginY + (mMaxWeight - weightInfo.getWeightFloat())/(mMaxWeight - mMinWeight)*internalY);
                }
                Log.d(TAG,"point = "+point);
                mPointLocations.add(point);
            }
        }
    }

    private void constructPath()
    {
        if(mPointLocations.size() <= 1)
        {
            return;
        }
        mPath = new Path();
        //多个点才画线
        for(int i = 0;i < mPointLocations.size();i += 2)
        {
            Point point1 = mPointLocations.get(i);
            mPath.moveTo(point1.x,point1.y);
            if((i + 1) == mPointLocations.size())
            {
                break;
            }
            Point point2 = mPointLocations.get(i+1);
            if((i + 2) == mPointLocations.size())
            {
                connectTwoPoints(point1,point2);
            }else{
                Point point3 = mPointLocations.get(i+2);
                connectThreePoints(point1,point2,point3);
            }
        }
    }

    private void connectTwoPoints(Point point1,Point point2)
    {
        if(point1.y > point2.y)
        {
            connectTwoPoints(point1,point2,CURVE_TYPE_QUTO_AHEAD_X);
        }else{
            connectTwoPoints(point1,point2,CURVE_TYPE_QUTO_AHEAD_X);
        }
    }

    private void connectTwoPoints(Point point1,Point point2,int curveType)
    {
        mPath.moveTo(point1.x,point1.y);
        if(curveType == CURVE_TYPE_QUTO_AHEAD_X)
        {
            float controlX = point1.x + (point2.x-point1.x)*CONTROL_PERCENT;
            float controlY = point1.y;
            mPath.quadTo(controlX,controlY,point2.x,point2.y);
        }else if(curveType == CURVE_TYPE_QUTO_AHEAD_Y) {
            float controlX = point1.x;
            float controlY = point1.y + (point2.y-point1.y)*CONTROL_PERCENT;
            mPath.quadTo(controlX,controlY,point2.x,point2.y);
        }else if(curveType == CURVE_TYPE_QUTO_BEHIND_Y) {
            float controlX = point2.x;
            float controlY = point2.y - (point2.y-point1.y)*CONTROL_PERCENT;
            mPath.quadTo(controlX,controlY,point2.x,point2.y);
        } else if(curveType == CURVE_TYPE_QUTO_BEHIND_X) {
            float controlX = point2.x - (point2.x-point1.x)*CONTROL_PERCENT;
            float controlY = point2.y;
            mPath.quadTo(controlX,controlY,point2.x,point2.y);
        }else if(curveType == CURVE_TYPE_CUBIC)
        {
            float controlX1 = point1.x + (point2.x-point1.x)*CONTROL_PERCENT;
            float controlY1 = point1.y;
            float controlX2 = point2.x - (point2.x-point1.x)*CONTROL_PERCENT;
            float controlY2 = point2.y;
            mPath.cubicTo(controlX1,controlY1,controlX2,controlY2,point2.x,point2.y);
        }
    }

    private void connectThreePoints(Point point1,Point point2,Point point3)
    {
        if((point1.y < point2.y && point2.y > point3.y) || (point1.y > point2.y && point2.y < point3.y))
        {
            connectTwoPoints(point1,point2,CURVE_TYPE_CUBIC);
            connectTwoPoints(point2,point3,CURVE_TYPE_CUBIC);
        }else{
            if(point1.y < point2.y) {
                connectTwoPoints(point1, point2, CURVE_TYPE_QUTO_BEHIND_X);
                connectTwoPoints(point2, point3, CURVE_TYPE_QUTO_AHEAD_Y);
            }else{
                Log.d(TAG,"here,"+point1+point2+point3);
//                connectTwoPoints(point1, point2, CURVE_TYPE_QUTO_BEHIND_Y);
//                connectTwoPoints(point2, point3, CURVE_TYPE_QUTO_AHEAD_X);
//                connectTwoPoints(point1, point2, CURVE_TYPE_QUTO_AHEAD_Y);
//                connectTwoPoints(point2, point3, CURVE_TYPE_QUTO_BEHIND_X);
                connectTwoPoints(point1, point2, CURVE_TYPE_CUBIC);
                connectTwoPoints(point2, point3, CURVE_TYPE_CUBIC);
            }
        }
    }

    public void setData(Date monthDate, List<WeightInfo> datas)
    {
        mMonthDate = monthDate;
//        mDatas = datas;
        makeFakeDatas();
        getMaxMinWeight();
        invalidate();
    }

    private void makeFakeDatas()
    {
        WeightInfo weightInfo1 = new WeightInfo();
        weightInfo1.setCreateTime("2021-07-07 00:00:00");
        weightInfo1.setWeight("20");
        mDatas.add(weightInfo1);

        WeightInfo weightInfo2 = new WeightInfo();
        weightInfo2.setCreateTime("2021-07-12 00:00:00");
        weightInfo2.setWeight("40");
        mDatas.add(weightInfo2);

        WeightInfo weightInfo3 = new WeightInfo();
        weightInfo3.setCreateTime("2021-07-20 00:00:00");
        weightInfo3.setWeight("30");
        mDatas.add(weightInfo3);

        WeightInfo weightInfo4 = new WeightInfo();
        weightInfo4.setCreateTime("2021-07-27 00:00:00");
        weightInfo4.setWeight("10");
        mDatas.add(weightInfo4);
    }

    public float getMaxWeight() {
        return mMaxWeight;
    }


    public float getMinWeight() {
        return mMinWeight;
    }

    private void getMaxMinWeight()
    {
        if(mDatas == null || mDatas.size() == 0)
        {
            return;
        }
        mMaxWeight = mDatas.get(0).getWeightFloat();
        mMinWeight = mDatas.get(0).getWeightFloat();

        for(int i = 1;i < mDatas.size();i++)
        {
            WeightInfo weightInfo = mDatas.get(i);
            if(weightInfo != null)
            {
                if(mMaxWeight < weightInfo.getWeightFloat())
                {
                    mMaxWeight = weightInfo.getWeightFloat();
                }
                if(mMinWeight > weightInfo.getWeightFloat())
                {
                    mMinWeight = weightInfo.getWeightFloat();
                }
            }
        }
    }

    private void drawCircles(Canvas canvas)
    {

    }
}
