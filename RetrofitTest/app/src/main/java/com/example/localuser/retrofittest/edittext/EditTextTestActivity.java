package com.example.localuser.retrofittest.edittext;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.localuser.retrofittest.Configs.LogConfigs;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.StatusBarUtil;
import com.googlecode.mp4parser.authoring.Edit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://blog.csdn.net/baidu_39510658/article/details/88788124
 * https://blog.csdn.net/ming_147/article/details/52120051
 */
public class EditTextTestActivity extends AppCompatActivity {
    private String TAG = LogConfigs.TAG_PREFIX_EDITTEXT_TEST + getClass().getSimpleName();
    private EditText mEditText;
    private TextView save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //下面代码无效，把底部按钮顶起来，无效
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        StatusBarUtil.setStatusBarDarkIcon(this, true, true);
        setContentView(R.layout.layout_edittext_test_activity);
        mEditText = findViewById(R.id.edittext);
//        mEditText.setText("text1text1text1text1text1");
//        mEditText.setText("dsfafdsdf");
//        mEditText.clearFocus();
//        mEditText.setSelected(false);
//        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                {
//                }
//            }
//        });
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick");
                ColorStateList redColors = ColorStateList.valueOf(Color.BLACK);
                String str = "text1text1text1text1text1";
                SpannableStringBuilder spanBuilder = new SpannableStringBuilder(str);
                //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
                //size  为0 即采用原始的正常的 size大小
                spanBuilder.setSpan(new TextAppearanceSpan(null, Typeface.BOLD, 0, redColors, null), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                mEditText.setHint(spanBuilder);
            }
        });
//        mEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(mEditText.getText() != null)
//                {
//                    mEditText.setText("");
//                }
//                return false;
//            }
//        });
        mEditText.addTextChangedListener(new MyEditTextChangeListener());
        //禁止输入emoji表情
        mEditText.setFilters(new InputFilter[]{mEmojiInputFilter});
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("test");
            }
        });
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

    public class EmojiInputFilter extends InputFilter.LengthFilter {
        private Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        public EmojiInputFilter(int max) {
            super(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                return "";
            }else{
                return super.filter(source, start, end, dest, dstart, dend);
            }
        }
    }

    public class M implements InputFilter{

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            return null;
        }
    }

    private EmojiInputFilter mEmojiInputFilter = new EmojiInputFilter(10);
}
