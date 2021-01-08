package com.example.localuser.retrofittest.Canvas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/12.
 */

public class CanvasActivity extends AppCompatActivity {
    private Button moveBtn;
    private MyTextView myTextView;
    private RoundRectImageView mRoundRectImageView;
    private CustomCircleImageView mCustomCircleImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_canvas_activity);
        moveBtn = (Button) findViewById(R.id.move);
        myTextView = (MyTextView) findViewById(R.id.text_view);
        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.move();
            }
        });

        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CanvasActivity.this,"click",Toast.LENGTH_LONG).show();
            }
        });
        mRoundRectImageView = findViewById(R.id.RoundRectImageView);
        mRoundRectImageView.setImageResource(R.mipmap.content_films);

        mCustomCircleImageView = findViewById(R.id.CustomCircleImageView);
        mCustomCircleImageView.setImageResource(R.mipmap.content_films);
    }
}
