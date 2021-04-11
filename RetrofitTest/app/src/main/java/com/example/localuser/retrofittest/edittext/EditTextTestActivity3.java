package com.example.localuser.retrofittest.edittext;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.localuser.retrofittest.Drawable.RoundRectDrawable;
import com.example.localuser.retrofittest.R;
import com.example.localuser.retrofittest.Utils.AppUtils;
import com.example.localuser.retrofittest.Utils.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

public class EditTextTestActivity3 extends AppCompatActivity implements View.OnClickListener{
    protected EditText mReceiverEt;
    protected ImageView mClearReceiverIv;
    protected EditText mTelephoneEt;
    protected ImageView mClearTelephoneIv;
    protected EditText mAreaEt;
    protected EditText mDetailAddressEt;
    protected ImageView mClearDetailAddressIv;
    protected TextView mSaveButton;
    protected RelativeLayout mContentLayout;
    protected ScrollView mScrollView;
    protected Button mButtton;
    protected Map<View, EditText> mClearMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().getDecorView().setBackground(null);
//        StatusBarUtil.setStatusBarDarkIcon(this, true, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_edittext_test3_main);
        initView();
    }

    protected void initView()
    {
        mSaveButton = (TextView) findViewById(R.id.save);
        mSaveButton.setBackground(new RoundRectDrawable(AppUtils.dp2px(22),true,false, Color.parseColor("#FF7F44"),Color.parseColor("#FFB611")));
        mSaveButton.setOnClickListener(this);
        mSaveButton.setTextSize(16);
        mReceiverEt = (EditText) findViewById(R.id.receiver_input);
        mClearReceiverIv = (ImageView) findViewById(R.id.clear_receiver);
        mClearReceiverIv.setOnClickListener(this);
        mClearMap.put(mClearReceiverIv,mReceiverEt);
        mTelephoneEt = (EditText) findViewById(R.id.telephone_input);
        //这是设置hint的字体大小
//        SpannableString ss = new SpannableString("我是测试hint");//定义hint的值
//        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,false);//设置字体大小 true表示单位是sp
//        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTelephoneEt.setHint(new SpannedString(ss));
        ColorStateList redColors = ColorStateList.valueOf(0xFFC0B5AB);
        String hint = getString(R.string.telephone_input_hint);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(hint);
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, 0, redColors, null), 0, hint.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mTelephoneEt.setHint(spanBuilder);
        mClearTelephoneIv = (ImageView) findViewById(R.id.clear_telephone);
        mClearTelephoneIv.setOnClickListener(this);
        mClearMap.put(mClearTelephoneIv,mTelephoneEt);
        mAreaEt = (EditText) findViewById(R.id.area_input);
        mAreaEt.setOnClickListener(this);
        mDetailAddressEt = (EditText) findViewById(R.id.detail_address_input);
        mClearDetailAddressIv = (ImageView) findViewById(R.id.clear_detail_address);
        mClearDetailAddressIv.setOnClickListener(this);
        mClearMap.put(mClearDetailAddressIv,mDetailAddressEt);
        mContentLayout = findViewById(R.id.content_layout);
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentLayout.getLayoutParams();
//        lp.height = outSize.y;
//        mContentLayout.setLayoutParams(lp);
        mScrollView = findViewById(R.id.scrollview);
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mScrollView.getLayoutParams();
//        lp.height = outSize.y;
//        mScrollView.setLayoutParams(lp);

        //软键盘不弹出
//        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mButtton = findViewById(R.id.save3);
//        mButtton.setTypeface(null,Typeface.NORMAL);
//        mButtton.setTypeface(Typeface.DEFAULT);
        mButtton.setTextSize(16);
    }

    @Override
    public void onClick(View v) {

    }
}
