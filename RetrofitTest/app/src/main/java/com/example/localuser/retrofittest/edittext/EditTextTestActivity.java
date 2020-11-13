package com.example.localuser.retrofittest.edittext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.googlecode.mp4parser.authoring.Edit;

public class EditTextTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_EDITTEXT_TEST + getClass().getSimpleName();
    private EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edittext_test_activity);
        mEditText = findViewById(R.id.edittext);
        mEditText.addTextChangedListener(new MyEditTextChangeListener());
    }

    private class MyEditTextChangeListener implements TextWatcher {

        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.i(TAG, "beforeTextChanged---" + charSequence.toString()+","+mEditText.getText());
        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.i(TAG, "onTextChanged---" + charSequence.toString()+","+mEditText.getText());
        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {
            Log.i(TAG, "afterTextChanged---"+editable.toString()+","+mEditText.getText());
        }
    }
}
