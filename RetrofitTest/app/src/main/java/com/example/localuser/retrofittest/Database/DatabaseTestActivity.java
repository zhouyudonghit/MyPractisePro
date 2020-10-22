package com.example.localuser.retrofittest.Database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class DatabaseTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this,"test",null,1);
        SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
//        database.insert();
    }
}
