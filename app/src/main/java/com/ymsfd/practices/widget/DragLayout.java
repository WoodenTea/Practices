package com.ymsfd.practices.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by WoodenTea.
 * Date: 2015/12/3
 * Time: 16:46
 */
public class DragLayout extends LinearLayout {
    private final String TAG = getClass().getSimpleName();
    private ViewDragHelper dragHelper;
    private View dragView;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            dragHelper.cancel();
            return false;
        }
        boolean bRet = dragHelper.shouldInterceptTouchEvent(ev);
        D("Intercept: " + bRet);
        return bRet;
    }

    private void D(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(0);
    }

    class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            D("Left: " + left + " Top: " + top + " dx: " + dx + " dy: " + dy);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            D("Edge Touched");
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            dragHelper.captureChildView(dragView, pointerId);
            D("Edge Drag Started");
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            D("clampViewPositionHorizontal " + left + " " + dx);
            final int boundLeft = getPaddingLeft();
            final int boundRight = getWidth() - dragView.getWidth() - getPaddingRight();

            int newLeft = Math.min(Math.max(left, boundLeft), boundRight);
            D("New Left: " + newLeft);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            D("clampViewPositionVertical " + top + " " + dy);
            final int boundTop = getPaddingTop();
            final int boundBottom = getHeight() - dragView.getHeight() - getPaddingBottom();
            return Math.min(Math.max(top, boundTop), boundBottom);
        }
    }
}
