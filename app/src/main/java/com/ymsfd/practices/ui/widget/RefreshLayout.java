package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * Created by WoodenTea.
 * Date: 2016/8/11
 * Time: 16:17
 */
public class RefreshLayout extends ViewGroup {
    private static final int INVALID_POINTER = -1;
    private static final float DRAG_RATE = .5f;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private int mTouchSlop;
    private View mTarget;
    private View mHeaderView;
    private boolean mReturningToStart;
    private boolean mRefreshing = false;
    private int mActivePointerId;
    private boolean mIsBeingDragged;
    private float mInitialDownY;
    private float mInitialMotionY;
    private int mOriginalOffsetTop;
    private int mCurrentTargetOffsetTop;
    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            moveToStart(interpolatedTime);
        }
    };
    private float mSpinnerFinalOffset;
    private float mTotalDragDistance = -1;
    private boolean mUsingCustomStart;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setWillNotDraw(false);
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);

        ViewCompat.setChildrenDrawingOrderEnabled(this, true);

        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mSpinnerFinalOffset = DEFAULT_CIRCLE_TARGET * metrics.density;
        mTotalDragDistance = mSpinnerFinalOffset;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        D("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        D("onInterceptTouchEvent");

        ensureTarget();

        if (!isEnabled() || mReturningToStart || canChildScrollUp() || mRefreshing) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTargetOffsetTopAndBottom(mOriginalOffsetTop - mHeaderView.getTop(), true);
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                mInitialDownY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                D("onInterceptTouchEvent->MOVE");
                if (mActivePointerId == INVALID_POINTER) {
                    E("Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }

                final float yDiff = y - mInitialDownY;
                D("y->" + y + " yDiff->" + yDiff + " mTouchSlop->" + mTouchSlop);
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mInitialMotionY = mInitialDownY + mTouchSlop;
                    mIsBeingDragged = true;
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }

        D("Intercept->" + mIsBeingDragged);
        return mIsBeingDragged;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && p instanceof LayoutParams;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        D("onLayout");
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }

        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();

        if (mHeaderView != null) {
            MarginLayoutParams params = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int headerLeft = childLeft + params.leftMargin;
            final int headerTop = mCurrentTargetOffsetTop;
            final int headerRight = headerLeft + mHeaderView.getMeasuredWidth();
            D("left->" + headerLeft + " top->" + headerTop + " right->" + headerRight);
            mHeaderView.layout(headerLeft, headerTop, headerRight, headerTop + mHeaderView
                    .getMeasuredHeight());
        }

        final View child = mTarget;
        LayoutParams params = (LayoutParams) child.getLayoutParams();
        final int leftMargin = params.leftMargin;
        final int topMargin = params.topMargin;
        final int rightMargin = params.rightMargin;
        final int bottomMargin = params.bottomMargin;
        child.layout(childLeft + leftMargin, childTop + topMargin,
                childLeft + childWidth - rightMargin,
                childTop + childHeight - bottomMargin);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex;
        D("onTouchEvent");
        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart || canChildScrollUp()) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    E("Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float moveY = MotionEventCompat.getY(ev, pointerIndex);
                final float moveOverScrollTop = (moveY - mInitialMotionY) * DRAG_RATE;
                if (mIsBeingDragged) {
                    if (moveOverScrollTop > 0) {
                        moveSpinner(moveOverScrollTop);
                    } else {
                        return false;
                    }
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    E("Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    E("Got ACTION_UP event but don't have an active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                finishSpinner(overScrollTop);
                mActivePointerId = INVALID_POINTER;
                return false;
            case MotionEvent.ACTION_CANCEL:
                return false;
        }
        return true;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        D("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        if (mHeaderView != null) {
            MarginLayoutParams params = (MarginLayoutParams) mHeaderView.getLayoutParams();
            measureChildWithMargins(mHeaderView,
                    widthMeasureSpec, paddingLeft + paddingRight,
                    heightMeasureSpec, 0);
            int headerHeight = mHeaderView.getMeasuredHeight() +
                    params.topMargin + params.bottomMargin;
            mCurrentTargetOffsetTop = mOriginalOffsetTop = -headerHeight;
        }

        mTarget.measure(
                makeMeasureSpec(getMeasuredWidth() - paddingLeft - paddingRight, MeasureSpec
                        .EXACTLY),
                makeMeasureSpec(getMeasuredHeight() - paddingTop - paddingBottom, MeasureSpec
                        .EXACTLY));
    }

    private void finishSpinner(float overScrollTop) {

    }

    private void moveSpinner(float overScrollTop) {
        D("moveSpinner");
        float originalDragPercent = overScrollTop / mTotalDragDistance;

        float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
        float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
        float extraOS = Math.abs(overScrollTop) - mTotalDragDistance;
        float slingshotDist = mUsingCustomStart ? mSpinnerFinalOffset - mOriginalOffsetTop
                : mSpinnerFinalOffset;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2)
                / slingshotDist);
        float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                (tensionSlingshotPercent / 4), 2)) * 2f;
        float extraMove = (slingshotDist) * tensionPercent * 2;

        int targetY = mOriginalOffsetTop + (int) ((slingshotDist * dragPercent) + extraMove);
        if (mHeaderView.getVisibility() != View.VISIBLE) {
            mHeaderView.setVisibility(View.VISIBLE);
        }

        D("targetY->" + targetY + " mCurrentTargetOffsetTop->" + mCurrentTargetOffsetTop);
        setTargetOffsetTopAndBottom(targetY - mCurrentTargetOffsetTop, true);
    }

    private void moveToStart(float interpolatedTime) {
        int targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
        int offset = targetTop - mHeaderView.getTop();
        setTargetOffsetTopAndBottom(offset, false);
    }

    private void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
        D("offset->" + offset);
        mHeaderView.bringToFront();
        mHeaderView.offsetTopAndBottom(offset);
        mCurrentTargetOffsetTop = mHeaderView.getTop();
        D("mCurrentTargetOffsetTop->" + mCurrentTargetOffsetTop);
        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                boolean b = ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
                D("canChildScrollUp->" + b);
                return b;
            }
        } else {
            boolean b = ViewCompat.canScrollVertically(mTarget, -1);
            D("canChildScrollUp->" + b);
            return b;
        }
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mHeaderView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    public void addHeader(View header) {
        if (header == null) {
            return;
        }

        if (mHeaderView != null && mHeaderView != header) {
            removeView(mHeaderView);
        }

        ViewGroup.LayoutParams params = header.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            header.setLayoutParams(params);
        }

        mHeaderView = header;
        addView(mHeaderView);
    }

    private void D(String message) {
        Log.d(getClass().getSimpleName(), message);
    }

    private void E(String message) {
        Log.e(getClass().getSimpleName(), message);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
