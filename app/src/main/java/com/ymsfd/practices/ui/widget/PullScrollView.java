package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.ymsfd.practices.infrastructure.util.DensityUtil;

import java.util.Locale;

/**
 * Created by WoodenTea.
 * Date: 2016/5/11
 * Time: 10:29
 */
public class PullScrollView extends ScrollView {
    private View childView;
    private View headerView;
    private float resistance = 0.25f;
    private float touchY;
    private Rect childRect = new Rect();
    private Rect headerRect = new Rect();
    private int childScrollHeight;
    private int headerScrollHeight;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
        childScrollHeight = (int) DensityUtil.dp2px(context, 200);
        headerScrollHeight = (int) DensityUtil.dp2px(context, 100);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (childView == null && getChildCount() > 0) {
                childView = getChildAt(0);
                childRect.set(childView.getLeft(), childView.getTop(),
                        childView.getRight(), childView.getBottom());
                D(String.format(Locale.getDefault(), "Child Rect->%s %s %s %s",
                        childRect.left, childRect.top, childRect.right, childRect.bottom));

                if (headerView != null) {
                    headerRect.set(headerView.getLeft(), headerView.getTop(),
                            headerView.getRight(), headerView.getBottom());
                }
            }

            if (childView != null) {
                touchY = ev.getY();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null && headerView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaY = ev.getY() - touchY;
                    if (isPullDown()) {
                        D("Pull Down, DeltaY->" + deltaY);

                        if (childView.getTop() == 0 && deltaY <= 0) {
                            return super.onTouchEvent(ev);
                        }

                        int needMove = (int) (deltaY * resistance);
                        int headMove;
                        int childMove;
                        headMove = needMove > headerScrollHeight ? headerScrollHeight :
                                needMove;
                        headerView.layout(headerRect.left, headerRect.top + headMove,
                                headerRect.right, headerRect.bottom + headMove);

                        needMove = (int) (needMove * 2.0f);
                        childMove = needMove > childScrollHeight ? childScrollHeight :
                                needMove;
                        if (childRect.top + childMove < childRect.top) {
                            childMove = 0;
                        }
                        childView.layout(childRect.left, childRect.top + childMove,
                                childRect.right, childRect.bottom + childMove);

                        return true;
                    } else {
                        touchY = ev.getY();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (isPullDown()) {
                        D("getTop->" + headerView.getTop() + " Top->" + headerRect.top);
                        TranslateAnimation animation = new TranslateAnimation(0, 0,
                                Math.abs(headerRect.top - headerView.getTop()), 0);
                        animation.setDuration(200);
                        headerView.startAnimation(animation);
                        headerView.layout(headerRect.left, headerRect.top,
                                headerRect.right, headerRect.bottom);

                        D("getTop->" + childView.getTop() + " Top->" + childRect.top);
                        TranslateAnimation childAnimation = new TranslateAnimation(0, 0,
                                childView.getTop(), childRect.top);
                        childAnimation.setDuration(200);
                        childView.startAnimation(childAnimation);
                        childView.layout(childRect.left, childRect.top,
                                childRect.right, childRect.bottom);
                        return true;
                    }
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }

    public void setHeader(View view) {
        headerView = view;
    }

    private boolean isPullDown() {
        return childView.getTop() >= 0 && getScrollY() == 0;
    }

    @SuppressWarnings("unused")
    public void scrollResistance(float resistance) {
        this.resistance = resistance;
    }

    private void D(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}
