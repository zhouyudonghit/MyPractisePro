package com.example.localuser.retrofittest.FileTest;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import java.io.File;

public class FileTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_FILETEST+getClass().getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,getFilesDir().getPath());
        Log.d(TAG,getFilesDir().getParent() + File.separator + getFilesDir().getName());
        File file1 = getExternalFilesDir(null);
        Log.d(TAG,"file1 = "+file1.getPath());
        File file2 = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        Log.d(TAG,"file2 = "+file2.getPath());
        File file3 = getExternalFilesDir("test");
        Log.d(TAG,"file3 = "+file3.getPath());
        Log.d(TAG,Environment.getExternalStorageState());
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data/");
        Log.d(TAG,dataDir.getPath());
        File dataDir2 = new File(new File(Environment.getExternalStorageDirectory(), "health"), "data/");
        Log.d(TAG,dataDir2.getPath());
        dataDir2.mkdir();
    }
}
