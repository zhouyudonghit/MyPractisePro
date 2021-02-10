package com.example.localuser.retrofittest.textviewtest;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.example.localuser.retrofittest.R;

public class TextViewTestActivity extends AppCompatActivity {
    private TextView textView;
    ForegroundColorSpan replayFgColorSpan;
    ForegroundColorSpan strReplayFgColorSpan;
    ForegroundColorSpan receiveFgColorSpan;
    ForegroundColorSpan colonFgColorSpan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_textview_test_activity);
        textView = findViewById(R.id.textview1);
        test2();
    }

    private void test1()
    {
        textView = findViewById(R.id.textview1);
        replayFgColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_C08A6D));
        strReplayFgColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_000022_80));
        receiveFgColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_C08A6D));
        colonFgColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_C08A6D));

        textView.setText(getReplySpannableString("json","tom","hello",null,null,null));
    }

    private SpannableString getReplySpannableString(String replyPerson, String receivePerson, String content, TextClick replyPersonTextClick,
                                                    TextClick receivePersonTextClick, TextClick contentTextClick) {

        String strReply = " " + "回复" + " ";
        String strColon = " " + "：" + " ";

        StringBuilder replyStrB = new StringBuilder(replyPerson);
        if (!TextUtils.isEmpty(receivePerson)) {
            replyStrB.append(strReply);
            replyStrB.append(receivePerson);
        }
        replyStrB.append(strColon);
        replyStrB.append(content);
        String reply = replyStrB.toString();

        SpannableString spannableString = new SpannableString(reply);

        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        // 处理回复者
        spannableString.setSpan(replyPersonTextClick, 0, replyPerson.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(replayFgColorSpan, 0, replyPerson.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 处理被回复者
        if (!TextUtils.isEmpty(receivePerson)) {
            // “回复”
            int strReplyStart = replyPerson.length();
            int strReplyEnd = strReplyStart + strReply.length();
            spannableString.setSpan(strReplayFgColorSpan, strReplyStart, strReplyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 被回复者
            int receiveStart = replyPerson.length() + strReply.length();
            int receiveEnd = receiveStart + receivePerson.length();
            spannableString.setSpan(receivePersonTextClick, receiveStart, receiveEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(receiveFgColorSpan, receiveStart, receiveEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //处理冒号
        int colonStart;
        int colonEnd;
        if (!TextUtils.isEmpty(receivePerson)) {
            colonStart = replyPerson.length() + strReply.length() + receivePerson.length();
        } else {
            colonStart = replyPerson.length();
        }
        colonEnd = colonStart + strColon.length();
        spannableString.setSpan(colonFgColorSpan, colonStart, colonEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 处理内容
        spannableString.setSpan(contentTextClick, colonEnd, reply.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 处理加粗
        int boldEnd = colonEnd;
        spannableString.setSpan(styleSpan_B, 0, boldEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private abstract class TextClick extends ClickableSpan {

        String mCustNum;

        public TextClick(String custNum) {
            mCustNum = custNum;
        }

        @Override
        public void onClick(View widget) {
            onClick(mCustNum);
        }

        abstract void onClick(String custNum);

        @Override
        public void updateDrawState(TextPaint ds) {
            // ds.setColor(mContext.getResources().getColor(R.color.pub_color_twenty));
        }
    }

    private void test2()
    {
        ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("这是一个测试");
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 0, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spanBuilder);
    }
}
