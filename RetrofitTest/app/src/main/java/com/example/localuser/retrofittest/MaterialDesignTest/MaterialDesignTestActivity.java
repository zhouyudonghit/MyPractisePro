package com.example.localuser.retrofittest.MaterialDesignTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class MaterialDesignTestActivity extends AppCompatActivity {
    private TextView textView1,textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design_test);
        initView();
    }

    private void initView()
    {
        textView1 = findViewById(R.id.floating_action_button_test);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialDesignTestActivity.this,FloatingActionButtonTestActivity.class));
            }
        });

        textView2 = findViewById(R.id.coordinatorlayout_test_test);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MaterialDesignTestActivity.this,CoordinatorLayoutRecyclerViewTestActivity.class));
                startActivity(new Intent(MaterialDesignTestActivity.this,CoordinatorLayoutViewPagerTestActivity.class));
            }
        });
    }
}
