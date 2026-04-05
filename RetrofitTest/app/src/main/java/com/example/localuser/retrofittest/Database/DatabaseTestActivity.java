package com.example.localuser.retrofittest.Database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DatabaseTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this,"test",null,1);
        SQLiteDatabase database = myDatabaseHelper.getReadableDatabase();
//        database.insert();
    }
}
