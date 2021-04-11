package com.example.localuser.retrofittest.MaskViewTest.masklayout2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

public class MaskLayout2 extends FrameLayout {
    public MaskLayout2(@NonNull Context context) {
        super(context);
    }

    public void addHighlightView(final View view)
    {
        view.post(new Runnable() {
            @Override
            public void run() {
                HoleDrawable drawable = new HoleDrawable(MaskLayout2.this,view);
                setBackground(drawable);
            }
        });
    }
}
