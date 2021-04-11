package com.example.localuser.retrofittest.ScreenRecordTest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.localuser.retrofittest.ListViewTest.ListViewTestActivity;
import com.example.localuser.retrofittest.R;

public class ScreenRecordTestActivity extends AppCompatActivity
{
    private Button start;
    private Button stop;
    boolean isrun = false;//用来标记录屏的状态private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;//录制视频的工具private int width, height, dpi;//屏幕宽高和dpi，后面会用到
    private ScreenRecorder screenRecorder;//这个是自己写的录视频的工具类，下文会放完整的代码
    Thread thread;//录视频要放在线程里去执行
    private MediaProjectionManager mediaProjectionManager;
    int height;
    int width;
    int dpi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screentest_main);
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });
        stop = findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;
        dpi = outMetrics.densityDpi;
        startActivity(new Intent(this, ListViewTestActivity.class));
        finish();
    }

    private void startRecord()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 103);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 104);
        }
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            intent = mediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(intent, 101);//正常情况是要执行到这里的,作用是申请捕捉屏幕
        } else {
            //ShowUtil.showToast(this, "Android版本太低，无法使用该功能");
        }
    }

    private void stopRecord()
    {
        screenRecorder.stop();
        start.setEnabled(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 102) {
            Toast.makeText(this, "缺少读写权限", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == 103) {
            Toast.makeText(this, "缺少录音权限", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == 104) {
            Toast.makeText(this, "缺少相机权限", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode != 101) {
            Log.e("HandDrawActivity", "error requestCode =" + requestCode);
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "捕捉屏幕被禁止", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        if (mediaProjection != null) {
            screenRecorder = new ScreenRecorder(width, height, mediaProjection, dpi);
        }
        thread = new Thread() {
            @Override
            public void run() {
                screenRecorder.startRecorder();//跟ScreenRecorder有关的下文再说，总之这句话的意思就是开始录屏的意思
            }
        };
        thread.start();
        //binding.startPlayer.setText("停止");//开始和停止我用的同一个按钮，所以开始录屏之后把按钮文字改一下
        start.setEnabled(false);
        isrun = true;//录屏状态改成真
    }
}
