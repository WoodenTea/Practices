package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by WoodenTea.
 * Date: 2016/5/10
 * Time: 10:33
 */
public class ReboundScrollView extends ScrollView {
    private final int REST_POSITION = 0x1;
    private View childView;
    private float touchY;
    private float scrollRatio = 0.4f;
    private Handler handler;
    private int step = 0;
    private int overScroll = 0;

    public ReboundScrollView(Context context) {
        this(context, null);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOverScrollMode(OVER_SCROLL_NEVER);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == REST_POSITION) {
                    if (overScroll != 0) {
                        overScroll -= step;
                        if ((overScroll > 0 && step < 0) || (overScroll < 0 && step > 0)) {
                            overScroll = 0;
                        }

                        childView.scrollTo(0, overScroll);
                        handler.sendEmptyMessageDelayed(REST_POSITION, 20);
                        return true;
                    }
                }

                return false;
            }
        });
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
        if (!isEnabled() || childView == null) {
            return super.onTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float deltaY = touchY - ev.getY();
                D("DeltaY->" + deltaY);
                touchY = ev.getY();
                if (deltaY >= 0 && isPullUp()) {
                    return super.onTouchEvent(ev);
                } else if (deltaY <= 0 && isPullDown()) {
                    return super.onTouchEvent(ev);
                }

                if (!isPullUp() || !isPullDown()) {
                    int needMove = (int) (deltaY * scrollRatio);
                    D("Had Up Move->" + childView.getScrollY()
                            + " Need Up Move->" + needMove);

                    childView.scrollBy(0, needMove);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                overScroll = childView.getScrollY();
                if (overScroll != 0) {
                    step = (int) (overScroll / 10.f);
                    handler.sendEmptyMessage(REST_POSITION);
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        D("onLayout");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        D("Action->" + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_DOWN && isEnabled()) {
            if (childView == null) {
                if (getChildCount() > 0) {
                    childView = getChildAt(0);
                    D("Child Height->" + childView.getMeasuredHeight() + " Parent Height->" +
                            getHeight());
                }
            }

            if (childView != null) {
                handler.removeMessages(REST_POSITION);
                childView.scrollTo(0, 0);
                touchY = ev.getY();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean isPullDown() {
        return canScrollVertically(-1);
    }

    private boolean isPullUp() {
        return canScrollVertically(1);
    }

    public void changedRatio(float ratio) {
        scrollRatio = ratio;
    }

    private void D(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}
