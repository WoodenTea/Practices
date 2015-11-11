package com.ymsfd.practices.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.ymsfd.practices.R;

public class SpannableActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_spannable);
        TextView tv = (TextView) findViewById(R.id.tv);
        String level = "等级: M1  ";
        String jewel = "emoji 55";

        SpannableString spannableLevel = new SpannableString(level);
        spannableLevel.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                D("1");
            }
        }, 0, level.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setHighlightColor(Color.TRANSPARENT);
        tv.append(spannableLevel);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString spannableJewel = new SpannableString(jewel);
        spannableJewel.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                D("2");
            }
        }, 0, jewel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Drawable drawable = getResources().getDrawable(R.drawable.cartoon);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        spannableJewel.setSpan(imageSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setHighlightColor(Color.TRANSPARENT);
        tv.append(spannableJewel);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        return true;
    }

}
