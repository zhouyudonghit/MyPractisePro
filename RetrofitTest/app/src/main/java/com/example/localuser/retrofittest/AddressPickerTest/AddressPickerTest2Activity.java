package com.example.localuser.retrofittest.AddressPickerTest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.linxz.addresspicker.AddressPickerSimpleActivity;

public class AddressPickerTest2Activity extends AppCompatActivity {
    private TextView mTvAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address_picker_test1_main);
        mTvAddress = findViewById(R.id.show_data);
        mTvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPickerView();
                AddressPickerSimpleActivity.launch(AddressPickerTest2Activity.this);
            }
        });
    }
}
