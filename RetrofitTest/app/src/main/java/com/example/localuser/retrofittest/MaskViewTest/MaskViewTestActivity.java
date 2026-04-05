package com.example.localuser.retrofittest.MaskViewTest;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.localuser.retrofittest.MaskViewTest.masklayout2.MaskLayout2;
import com.example.localuser.retrofittest.R;

public class MaskViewTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maskview_test_activity);
        addMaskView();
    }

    private void addMaskView()
    {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        MaskLayout2 maskLayout = new MaskLayout2(this);
        decorView.addView(maskLayout, lp);
//        addContentView(maskLayout, lp);

        maskLayout.addHighlightView(findViewById(R.id.test));
    }
}
