package com.example.localuser.retrofittest.NoneMainThreadOpUI;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.localuser.retrofittest.MainActivity;
import com.example.localuser.retrofittest.R;

public class NoneMainThreadOpUIActivity extends AppCompatActivity {
    public static String TAG_PREFIX = "NoneMainThreadOpUI--";
    private String TAG = TAG_PREFIX+getClass().getSimpleName();
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonemainthread_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NonUiThread thread = new NonUiThread();
                thread.start();
            }
        });
    }

    class NonUiThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            TextView tx = new TextView(NoneMainThreadOpUIActivity.this);
            tx.setText("non-UiThread update textview");
            tx.setTextColor(Color.RED);

            WindowManager windowManager = NoneMainThreadOpUIActivity.this.getWindowManager();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    200, 200, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                    WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.TRANSPARENT);
            windowManager.addView(tx, params);
            Looper.loop();
        }
    }

}
