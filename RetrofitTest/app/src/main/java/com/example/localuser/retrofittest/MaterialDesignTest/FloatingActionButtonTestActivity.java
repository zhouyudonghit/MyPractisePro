package com.example.localuser.retrofittest.MaterialDesignTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.Toast;

import com.example.localuser.retrofittest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FloatingActionButtonTestActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_action_button_test);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"是否删除数据", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FloatingActionButtonTestActivity.this,"您取消了删除",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
}
