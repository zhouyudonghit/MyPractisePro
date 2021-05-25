package com.example.localuser.retrofittest.Canvas;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class PathTest extends BaseTest {
    private Path mPath;
    public PathTest(View view) {
        super(view);
        mPath = new Path();
    }

    public void drawPath1(Canvas canvas)
    {
        mPath.reset();
        mPath.addArc(200, 200, 400, 400, -225, 225);
//        mPath.addArc(400, 200, 600, 400, -180, 225);
        mPath.arcTo(400, 200, 600, 400, -180, 225,false);
        mPath.lineTo(400, 542);
        mPath.close();
//        mPaint.setStrokeWidth(10);
//        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath2(Canvas canvas)
    {
        mPath.reset();
        mPath.addCircle(mViewWidth/4,mViewHeight/2,mViewHeight/4,Path.Direction.CW);
        mPath.addCircle(mViewWidth/2,mViewHeight/2,mViewHeight/4,Path.Direction.CCW);
        mPath.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath3(Canvas canvas)
    {
        mPath.reset();
        mPath.lineTo(mViewWidth/2,mViewHeight/2);
        //lineTo传进的参数都是绝对坐标的参数
        mPath.lineTo(mViewWidth/2,mViewHeight/3);
        //rLineTo传进的参数都是以当前点为坐标系原点的参数，是相对坐标
        mPath.rLineTo(100,100);
        //mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath4(Canvas canvas)
    {
        mPath.reset();
        //mPath.quadTo(mViewWidth,0,mViewWidth/2,mViewHeight/2);
        mPath.lineTo(mViewWidth/2,mViewHeight/2);
        mPath.rQuadTo(mViewWidth/2,0,mViewWidth/2,mViewHeight/2);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath5(Canvas canvas)
    {
        mPath.reset();
        mPath.lineTo(100, 100); // 画斜线
        mPath.moveTo(200, 100); // 我移~~
        mPath.lineTo(200, 0); // 画竖线
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath6(Canvas canvas)
    {
        mPath = new Path();
        Path newPath = new Path();
        //mPath.addCircle(mViewWidth/4,mViewHeight/2,mViewWidth/4,Path.Direction.CW);
        mPath.lineTo(mViewWidth,mViewHeight/2);
        mPath.lineTo(mViewWidth/2,mViewHeight);
        //newPath.addCircle(mViewWidth/2,mViewHeight/2,mViewWidth/4,Path.Direction.CW);
        newPath.moveTo(mViewWidth/2,0);
        newPath.lineTo(mViewWidth/2,mViewHeight);
        mPath.op(newPath,Path.Op.INTERSECT);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawPath(mPath,mPaint);
    }

    public void drawPath7(Canvas canvas)
    {
        mPath.reset();
        //画矩形的时候，两个线的方向相同，没有效果
        mPath.addRect(0,0,mViewWidth*3/4,mViewHeight,Path.Direction.CCW);
        mPath.addRect(mViewWidth*2/4,0,mViewWidth,mViewHeight,Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * Clip(剪切)的时机：通常理解的clip(剪切)，是对已经存在的图形进行clip的。但是，在android上是对canvas（画布）上进行clip的，要在画图之前对canvas进行clip，如果画图之后再对canvas进行clip不会影响到已经画好的图形。一定要记住clip是针对canvas而非图形
     *
     * Clip中的Op的参数的意思：
     *
     * DIFFERENCE是第一次不同于第二次的部分显示出来A-B-------
     *
     * REPLACE是显示第二次的B******
     *
     * REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示--------
     *
     * INTERSECT交集显示A-(A-B)*******
     *
     * UNION全部显示A+B******
     *
     * XOR补集 就是全集的减去交集生育部分显示--------
     * @param canvas
     */
    public void clipPath1(Canvas canvas){
        Path path = new Path();
        path.addRect(mViewWidth/4,0,mViewWidth*3/4,mViewHeight,Path.Direction.CCW);
        canvas.clipPath(path);
    }
}
