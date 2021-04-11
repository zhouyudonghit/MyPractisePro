package com.example.localuser.retrofittest.MemoryTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MemoryTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MemoryTest memoryTest = new MemoryTest(this);
        memoryTest.test();
    }
}
