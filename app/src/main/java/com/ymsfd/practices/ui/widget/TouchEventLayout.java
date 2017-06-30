package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by WoodenTea.
 * Date: 2016/8/12
 * Time: 16:25
 */
public class TouchEventLayout extends LinearLayout {
    public TouchEventLayout(Context context) {
        super(context);
    }

    public TouchEventLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        D("dispatch");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        D("onIntercept");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        D("onTouch");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void D(String message) {
        Log.d(getClass().getSimpleName(), message);
    }
}
