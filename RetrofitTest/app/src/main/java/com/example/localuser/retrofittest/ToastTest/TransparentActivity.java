package com.example.localuser.retrofittest.ToastTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.localuser.retrofittest.R;

public class TransparentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transparent_activity);
    }
}
