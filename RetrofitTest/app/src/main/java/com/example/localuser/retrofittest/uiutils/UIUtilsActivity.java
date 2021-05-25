package com.example.localuser.retrofittest.uiutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.example.localuser.retrofittest.Utils.ScreenUtils;
import com.googlecode.mp4parser.authoring.Edit;

public class UIUtilsActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_UIUTILS + getClass().getSimpleName();
    private EditText dpEt;
    private EditText pxEt;
    private TextView appShowAreaSize;
    private TextView realSize;
    private TextView dp_pxTv;
    private static int NUMBER = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_uiutil_activity);
        initView();
        Log.d(TAG,"onCreate,NUMBER = "+NUMBER);
    }

    private void initView()
    {
        dp_pxTv = findViewById(R.id.dp_px);
        appShowAreaSize = findViewById(R.id.app_show_area_size);
        realSize = findViewById(R.id.real_screen_size);
        dpEt = findViewById(R.id.dp_et);
        pxEt = findViewById(R.id.px_et);
        dp_pxTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dp = dpEt.getText().toString().trim();
                String px = pxEt.getText().toString().trim();
                if(!TextUtils.isEmpty(dp))
                {
                    try {
                        float value = Float.valueOf(dp);
                        pxEt.setText(AppUtils.dip2px(UIUtilsActivity.this,value)+"");
                    }catch (Exception e)
                    {
                        Log.d(TAG,"",e);
                    }
                }else if(!TextUtils.isEmpty(px))
                {
                    try {
                        float value = Float.valueOf(px);
                        dpEt.setText(AppUtils.px2dip(UIUtilsActivity.this,value));
                    }catch (Exception e)
                    {
                        Log.d(TAG,"",e);
                    }
                }
            }
        });

        showScreenSize();
    }

    private void showScreenSize()
    {
        Window window = getWindow();
        appShowAreaSize.setText("应用程序显示区域 宽："+ ScreenUtils.getWindowWidth1(window)+",高："+ScreenUtils.getWindowHeight1(window));
        realSize.setText("实际显示区域 宽："+ ScreenUtils.getWindowWidth2(window)+",高："+ScreenUtils.getWindowHeight2(window));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NUMBER = 0;
        Log.d(TAG,"onDestroy(),NUMBER = "+NUMBER);
    }
}
