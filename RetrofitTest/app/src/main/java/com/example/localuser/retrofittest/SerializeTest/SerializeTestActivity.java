package com.example.localuser.retrofittest.SerializeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.localuser.retrofittest.R;

public class SerializeTestActivity extends AppCompatActivity {
    public static String KEY1 = "parcel";
    public static String KEY2 = "parcel2";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize_test_main);
        Button button = findViewById(R.id.jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SerializeTestActivity.this,JumpActivity.class);
                B b = new B();
                b.setA(1);
                b.setB(2);
                intent.putExtra(KEY1,b);

                C c = new C();
                c.setA(1);
                c.setB(2);
                c.setC(3);
                intent.putExtra(KEY2,c);
                startActivity(intent);
            }
        });
    }
}
