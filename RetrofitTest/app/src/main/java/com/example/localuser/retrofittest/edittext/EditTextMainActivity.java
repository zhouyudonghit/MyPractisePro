package com.example.localuser.retrofittest.edittext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class EditTextMainActivity extends AppCompatActivity {
    private TextView textView1,textView2,textView3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edittext_main_activity);
        initView();
    }

    private void initView()
    {
        textView1 = findViewById(R.id.button1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTextMainActivity.this,EditTextTestActivity.class);
                startActivity(intent);
            }
        });

        textView2 = findViewById(R.id.button2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTextMainActivity.this,EditTextTestActivity2.class);
                startActivity(intent);
            }
        });

        textView3 = findViewById(R.id.button3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTextMainActivity.this,EditTextTestActivity3.class);
                startActivity(intent);
            }
        });

        textView1.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout = (FrameLayout) getWindow().getDecorView();
            }
        });
    }
}
