package com.example.localuser.retrofittest.Toolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.localuser.retrofittest.R;

/**
 * Created by localuser on 2018/4/11.
 */

public class ToolbarActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toolbar_activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("toolbar_test");
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
