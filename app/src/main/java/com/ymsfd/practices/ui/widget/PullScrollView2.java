package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import java.util.Locale;

/**
 * Created by WoodenTea.
 * Date: 2016/5/11
 * Time: 10:29
 */
public class PullScrollView2 extends ScrollView {
    private View childView;
    private float resistance = 0.8f;
    private float touchY;
    private Rect childRect = new Rect();

    public PullScrollView2(Context context) {
        this(context, null);
    }

    public PullScrollView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (childView == null && getChildCount() > 0) {
                childView = getChildAt(0);

                childRect.set(getLeft(), getTop(), getRight(), getBottom());
                D(String.format(Locale.getDefault(), "Child Rect->%s %s %s %s",
                        childRect.left, childRect.top, childRect.right, childRect.bottom));
            }

            if (childView != null) {
                touchY = ev.getY();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isPullUp() && !isPullDown()) {
            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float deltaY = ev.getY() - touchY;
                    D("DeltaY->" + deltaY);
                    int needMove = (int) (deltaY * resistance);
                    D("Top->" + getTop() + " Bottom->" + getBottom());
                    if (deltaY < 0 && isPullUp() && getBottom() == childRect.bottom) {
                        return super.onTouchEvent(ev);
                    } else if (deltaY > 0 && isPullDown() && getTop() == childRect.top) {
                        return super.onTouchEvent(ev);
                    } else if (!isPullDown() || !isPullUp()) {
                        layout(childRect.left, childRect.top + needMove,
                                childRect.right, childRect.bottom + needMove);

                        return true;
                    } else {
                        touchY = ev.getY();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (getTop() > childRect.top) {
                        D("getTop->" + getTop() + " Top->" + childRect.top);
                        if (getTop() > childRect.top + (childRect.bottom - childRect.top) / 5) {
                            TranslateAnimation childAnimation = new TranslateAnimation(0, 0,
                                    getTop(), childRect.bottom);
                            childAnimation.setDuration(300);
                            startAnimation(childAnimation);
                            setVisibility(GONE);
                        } else {
                            TranslateAnimation childAnimation = new TranslateAnimation(0, 0,
                                    getTop(), 0);
                            childAnimation.setDuration(300);
                            startAnimation(childAnimation);
                            layout(childRect.left, childRect.top,
                                    childRect.right, childRect.bottom);
                        }

                        return true;
                    } else if (getBottom() < childRect.bottom) {
                        D("getBottom->" + getBottom() + " Bottom->" + childRect.bottom);
                        D("Bottom->" + (childRect.bottom - (childRect.bottom - childRect.top) /
                                5));
                        if (getBottom() > childRect.bottom - (childRect.bottom - childRect.top) /
                                5) {
                            TranslateAnimation childAnimation = new TranslateAnimation(0, 0,
                                    getTop(), childRect.top);
                            childAnimation.setDuration(300);
                            startAnimation(childAnimation);
                            layout(childRect.left, childRect.top,
                                    childRect.right, childRect.bottom);
                        } else {
                            TranslateAnimation childAnimation = new TranslateAnimation(0, 0,
                                    getTop(), -(childRect.bottom - childRect.top));
                            childAnimation.setDuration(300);
                            startAnimation(childAnimation);
                            setVisibility(GONE);
                        }

                        return true;
                    }
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }

    private boolean isPullDown() {
        return canScrollVertically(-1);
    }

    private boolean isPullUp() {
        return canScrollVertically(1);
    }

    @SuppressWarnings("unused")
    public void scrollResistance(float resistance) {
        this.resistance = resistance;
    }

    private void D(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}
