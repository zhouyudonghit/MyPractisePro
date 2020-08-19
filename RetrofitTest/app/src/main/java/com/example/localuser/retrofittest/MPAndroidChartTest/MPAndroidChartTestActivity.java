package com.example.localuser.retrofittest.MPAndroidChartTest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class MPAndroidChartTestActivity extends AppCompatActivity {
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
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            if(i != 10) {
//                entries.add(new Entry(i, (float) (Math.random()) * 80));
//            }
//        }
        entries.add(new Entry(5,40));
        entries.add(new Entry(6,40));
        entries.add(new Entry(7,40));
        entries.add(new Entry(8,40));
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
        lineDataSet.setDrawValues(false);
        lineDataSet.setFillColor(Color.RED);
        lineDataSet.setColor(Color.RED);

        List<Entry> entries2 = new ArrayList<>();
//        entries2.add(new Entry(10,40));
//        for (int i = 0; i < 10; i++) {
//            entries2.add(new Entry(i, (float) (Math.random()) * 80));
//        }
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "温度2");
//        data.addDataSet(lineDataSet2);
//        data.notifyDataChanged();
        test();
        mLineChart.setData(data);
        //调用这个方法才能使moveViewTo有效
        mLineChart.setVisibleXRange(5f,5f);
    }

    private void testButton()
    {
        mLineChart.setVisibleXRange(1f,1f);
        mLineChart.invalidate();
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
        mLineChart.moveViewToX(3);
        mLineChart.post(new Runnable() {
            @Override
            public void run() {
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
            }
        });
    }

    private void test()
    {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);//上下都有横坐标
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);//坐标的值在视图内部，即坐标系里面。默认是在坐标系外面。
        xAxis.setGranularity(1);
        xAxis.setLabelCount(10,true);
        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(10f);

        YAxis leftYAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);

        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);

        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "%";
            }
        });
//        rightYAxis.setEnabled(false);

        rightYAxis.setGranularity(1f);
        rightYAxis.setLabelCount(11,false);
        rightYAxis.setTextColor(Color.BLUE); //文字颜色
        rightYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色

        LimitLine limitLine = new LimitLine(95,"高限制性"); //得到限制线
        limitLine.setLineWidth(4f); //宽度
        limitLine.setTextSize(10f);
        limitLine.setTextColor(Color.RED);  //颜色
        limitLine.setLineColor(Color.BLUE);
        rightYAxis.addLimitLine(limitLine); //Y轴添加限制线

        Legend legend = mLineChart.getLegend();
        legend.setTextColor(Color.CYAN); //设置Legend 文本颜色
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//        legend.setEnabled(false);//是否显示
        legend.setWordWrapEnabled(true);//设置标签是否换行（当多条标签时 需要换行显示、如上右图）true：可换行。false：不换行

        Description description = new Description();
//        description.setEnabled(false);
        description.setText("X轴描述");
        description.setTextColor(Color.RED);
        mLineChart.setDescription(description);

//        MyMarkerView mv = new MyMarkerView(this);
//        mLineChart.setMarkerView(mv);
    }
}
