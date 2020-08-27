package com.example.localuser.retrofittest.MPAndroidChartTest;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class MPAndroidChartTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_MPANDROIDCHART + getClass().getSimpleName();
    private LineChart mLineChart;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart_test_main);
        mLineChart = (LineChart) findViewById(R.id.lineChart);
        button = findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testButton();
            }
        });
        testData();
        testAxis();
        testLineChart();
        testLegend();
        testDescription();
        testMarkerView();
    }

    private void testButton()
    {
        mLineChart.setVisibleXRange(1f,1f);
        mLineChart.invalidate();
    }

    private void testLineChart()
    {
        //设置整个view的背景
//        mLineChart.setBackgroundColor(Color.YELLOW);
        //如果启用，chart 绘图区后面的背景矩形将绘制
        mLineChart.setDrawGridBackground(false);
        mLineChart.setGridBackgroundColor(Color.BLUE);
        mLineChart.setDrawBorders(true);
        mLineChart.setBorderColor(Color.YELLOW);
        mLineChart.setBorderWidth(10);
        //设置最大可见绘制的 chart count 的数量。 只在 LineData.setDrawValues() 设置为 true 时有效
        /**
         * the maximum number of entries to which values will be drawn
         * (entry numbers greater than this value will cause value-labels to disappear)
         * 还需要看一下DataRenderer的isDrawingValuesAllowed(ChartInterface chart)方法，这里面才能真正理解该方法的用法
         */
//        mLineChart.setMaxVisibleValueCount(8);
//        //调用这个方法才能使moveViewTo有效
//        mLineChart.setVisibleXRange(7f,7f);
        //        mLineChart.setVisibleXRangeMaximum(18);
//        mLineChart.setVisibleXRangeMinimum(5);
        //        mLineChart.moveViewToX(3);
//        mLineChart.post(new Runnable() {
//            @Override
//            public void run() {
//                mLineChart.notifyDataSetChanged();
//                mLineChart.invalidate();
//            }
//        });
        testInteractionWithChart();
    }

    private void testInteractionWithChart()
    {
        //启用/禁用与图表的所有可能的触摸交互
//        mLineChart.setTouchEnabled(false);
        //启用/禁用拖动（平移）图表
//        mLineChart.setDragEnabled(false);
        //启用/禁用缩放图表上的两个轴
//        mLineChart.setScaleEnabled(false);
        //启用/禁用缩放在x轴上
//        mLineChart.setScaleXEnabled(false);
        //启用/禁用缩放在y轴
//        mLineChart.setScaleYEnabled(false);
        //如果设置为true，捏缩放功能,x和y可以同时缩放。 如果false，x轴和y轴只可分别放大
//        mLineChart.setPinchZoom(true);
        //设置为false以禁止通过在其上双击缩放图表。设置为true，双击后x和y同时放大
//        mLineChart.setDoubleTapToZoomEnabled(false);
        //如果设置为true，当图表缩小至最小时，手指在图表上滑动，经过数据点时会高亮value，否则不会高亮value。
//        mLineChart.setHighlightPerDragEnabled(true);
        //如果设置为false，当图表缩小至最小时，单击图表，不会高亮value；当为true时，当图表缩小至最小时，单击图表，会高亮value
//        mLineChart.setHighlightPerTapEnabled(false);
        //如果设置为true，手指滑动抛掷图表后继续减速滚动,否则手指滑动图表停止后，图表也立即停止滑动。 默认值：true
//        mLineChart.setDragDecelerationEnabled(false);
        //减速的摩擦系数在[0; 1]区间，数值越高表示速度会缓慢下降，例如，如果将其设置为0，将立即停止。 1是一个无效的值，会自动转换至0.9999。
//        mLineChart.setDragDecelerationFrictionCoef(0.3f);
        //下面代码会高亮x坐标为4的值，高亮就是显示y坐标值
        Highlight[] highlights = new Highlight[1];
        Highlight highlight1 = new Highlight(4.0f,30.0f,0);
        highlights[0] = highlight1;
        mLineChart.highlightValues(highlights);
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG,"onValueSelected, e = "+e + ",h = "+h);
            }

            @Override
            public void onNothingSelected() {
                Log.d(TAG,"onNothingSelected");
            }
        });

        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }

    private void testData()
    {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if(i % 2 == 0) {
                entries.add(new Entry(i, (float) (Math.random()) * 80));
            }
        }
//        entries.add(new Entry(5,40));
//        entries.add(new Entry(6,40));
//        entries.add(new Entry(7,40));
//        entries.add(new Entry(8,40));
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        LineData data = new LineData(lineDataSet);
        lineDataSet.setDrawCircleHole(false);
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);//是否默认显示y坐标
        //Sets the color that is used for filling the area below the line.
//        lineDataSet.setFillColor(Color.RED);
        //下面可以设置填充颜色为渐变
        Drawable fillDrawable = ContextCompat.getDrawable(this, R.drawable.chartlib_fade_accent);
        lineDataSet.setFillDrawable(fillDrawable);
        lineDataSet.setColor(Color.RED);
        //是否允许高亮，如果设置为false，
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setHighlightLineWidth(5);

        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(10,40));
//        for (int i = 0; i < 10; i++) {
//            entries2.add(new Entry(i, (float) (Math.random()) * 80));
//        }
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "温度2");
//        data.addDataSet(lineDataSet2);
//        data.notifyDataChanged();
//        data.getDataSetByIndex(0);//返回第几个数据集合
        //检查此数据对象是否包含指定的Entry 。 注：这个相当影响性能，性能严峻情况下，不要过度使用。
//        lineDataSet.contains(new Entry());
        //清除所有 DataSet 对象和所有 Entries 的数据 。 不会删除所提供的 x-values 。
//        data.clearValues();
        mLineChart.setData(data);
    }

    private void addGestureListener()
    {
        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void testAxis()
    {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);//上下都有横坐标
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);//坐标的值在视图内部，即坐标系里面。默认是在坐标系外面。
        xAxis.setGranularity(1);
        xAxis.setLabelCount(10,true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(20f);
        xAxis.setEnabled(true);//如果设置为false，不会画横坐标轴那条线，也不会画垂直于坐标轴的网络线
        xAxis.setDrawAxisLine(false);//不会画横坐标那条线
        xAxis.setDrawGridLines(false);//不会画垂直于坐标轴的网络线
        xAxis.setDrawLabels(true);//是否显示横坐标轴的值，即x坐标轴的标签
        xAxis.setAvoidFirstLastClipping(true);
        Log.d(TAG,"mLineChart.getXRange() = "+mLineChart.getXRange());

        YAxis leftYAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
//        leftYAxis.setAxisMinimum(0f);
//        leftYAxis.setAxisMaximum(100f);
        //设置图表中的最高值的顶部间距占最高值的值的百分比（设置的百分比 = 最高柱顶部间距/最高柱的值）。默认值是10f，即10%
        //该方法想要生效，必须是能够自动计算y轴的最大值，即不设置最大值，即不调用setAxisMaximum
//        leftYAxis.setSpaceTop(100f);
        //设置图表中的最小值的底部间距占最高值的值的百分比（设置的百分比 = 最低柱底部间距/最高柱的值）。默认值是10f，即10%
        //该方法想要生效，必须是能够自动计算y轴的最小值，即不设置最小值，即不调用setAxisMinimum
        leftYAxis.setSpaceBottom(100f);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "%";
            }
        });
//        leftYAxis.setInverted(true);
        LimitLine limitLine = new LimitLine(30,"限制线"); //得到限制线
        limitLine.setLineWidth(4f); //宽度
        limitLine.setTextSize(10f);
        limitLine.setTextColor(Color.RED);  //颜色
        limitLine.setLineColor(Color.BLUE);
        leftYAxis.addLimitLine(limitLine); //Y轴添加限制线
        leftYAxis.setDrawLimitLinesBehindData(true);//限制线是画在图表前面，还是图表后面
        leftYAxis.setEnabled(true);

        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);

        rightYAxis.setEnabled(false);

        rightYAxis.setGranularity(1f);
        rightYAxis.setLabelCount(11,false);
        rightYAxis.setTextColor(Color.BLUE); //文字颜色
        rightYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色
    }

    private void testLegend()
    {
        Legend legend = mLineChart.getLegend();
        legend.setTextColor(Color.CYAN); //设置Legend 文本颜色
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//        legend.setEnabled(false);//是否显示
        legend.setWordWrapEnabled(true);//设置标签是否换行（当多条标签时 需要换行显示、如上右图）true：可换行。false：不换行
    }

    private void testDescription()
    {
        Description description = new Description();
//        description.setEnabled(false);
        description.setText("X轴描述");
        description.setTextColor(Color.RED);
        mLineChart.setDescription(description);
    }

    private void testMarkerView()
    {
        MyMarkerView mv = new MyMarkerView(this);
        mLineChart.setMarkerView(mv);
    }
}
