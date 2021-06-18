package com.example.localuser.retrofittest.ToastTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;

public class ToastTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_TOAST_TEST + getClass().getSimpleName();
    private TextView showToast,dismissToast;
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toast_test_activity);
        showToast = findViewById(R.id.show_toast);
        showToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast1();
            }
        });
        dismissToast = findViewById(R.id.dismiss_toast);
        dismissToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"dismissToast click");
                dismissToast();
            }
        });
    }

    private void showToast()
    {
        mToast = Toast.makeText(this,"test test test test test test test test",Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.TOP,0,200);
        mToast.show();
    }

    private void showToast1()
    {
        mToast = Toast.makeText(this,"test test",Toast.LENGTH_SHORT);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast,null);
        TextView textView = layout.findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"textView click");
                mToast.cancel();
            }
        });
//        mToast.setGravity(Gravity.TOP,0,200);
//        mToast.setMargin();
        mToast.setView(layout);
        mToast.show();
    }

    /**
     * 透明activity不好使，手势透不下去
     */
    private void showToast2()
    {
        Intent intent = new Intent(this,TransparentActivity.class);
        startActivity(intent);
    }

    private void dismissToast()
    {
        mToast.cancel();
    }
}
