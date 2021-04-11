package com.example.localuser.retrofittest.SmallMethodTest;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class SmallMethodTestActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "SmallMethodTest---";
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_smallmethodtest_main);
        final TextView tv = (TextView) findViewById(R.id.textview);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLayoutParamForAddView();
            }
        });
    }

    /**
     * 测试setLayoutParams是否刷新界面
     * @param v
     */
    public void testLayoutParamForInvalidate(View v)
    {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        Log.d(TAG,"lp.getheight = " + lp.height);
        Log.d(TAG,"tv.getheight = " + v.getHeight());
        if(lp.height <= 0)
        {
            lp.height = 2*v.getHeight();
        }
        v.setLayoutParams(lp);
    }

    /**
     * 测试setLayoutParam是否可以显示新增的View
     */
    public void testLayoutParamForAddView()
    {
        TextView textView = new TextView(this);
        textView.setText("new textview");
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);
        textView.layout(0,0,100,100);
    }
}
