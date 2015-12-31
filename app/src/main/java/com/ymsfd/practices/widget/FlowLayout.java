package com.ymsfd.practices.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WoodenTea.
 * Date: 2015/11/10
 * Time: 16:18
 */
public class FlowLayout extends ViewGroup {
    private final String TAG = getClass().getSimpleName();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthUsed = getPaddingLeft() + getPaddingRight();
        int parentWidth = widthSize - widthUsed;
        int maxWidth = 0;
        int maxHeight = 0;
        int drawLeft = getPaddingLeft();
        int lineHeight = 0;
        int childWidth;
        int childHeight;
        int childCount = getChildCount();
        boolean lastChild = false;

        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            lastChild = (index == childCount - 1);
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, 0);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (drawLeft + childWidth > parentWidth) {
                drawLeft = getPaddingLeft();
                maxHeight += lineHeight;
                lineHeight = childHeight;
            } else {
                drawLeft += childWidth;
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight() + lp.topMargin + lp
                        .bottomMargin);
                maxWidth = Math.max(maxWidth, drawLeft);
            }
        }

        if (lastChild) {
            maxHeight += lineHeight;
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : maxWidth,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize :
                        maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int drawTop = getPaddingTop();
        int drawLeft = getPaddingLeft();

        int childWidth;
        int childHeight;
        int childTopMargin;
        int childLeftMargin;
        int childRightMargin;
        int childBottomMargin;
        int horizontalMargin;
        int verticalMargin;

        int maxHeight = 0;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            childLeftMargin = lp.leftMargin;
            childTopMargin = lp.topMargin;
            childRightMargin = lp.rightMargin;
            childBottomMargin = lp.bottomMargin;

            horizontalMargin = childLeftMargin + childRightMargin;
            verticalMargin = childTopMargin + childBottomMargin;

            if (drawLeft + childWidth + horizontalMargin > parentWidth) {
                drawTop += maxHeight + childTopMargin;
                maxHeight = 0;
                drawLeft = getPaddingLeft() + lp.leftMargin;
                child.layout(drawLeft, drawTop, drawLeft + childWidth, drawTop + childHeight);
                D("l: " + drawLeft + " t: " + drawTop + " r: " + (drawLeft + childWidth) + " b: " +
                        (drawTop + childHeight));
                drawLeft += childWidth + childRightMargin;
            } else {
                drawLeft += childLeftMargin;
                child.layout(drawLeft, drawTop + childTopMargin, drawLeft + childWidth, drawTop +
                        childTopMargin + childHeight);
                D("l: " + drawLeft + " t: " + (drawTop + childTopMargin) + " r: " + (drawLeft +
                        childWidth) + " b: " + (drawTop + childTopMargin + childHeight));
                drawLeft += childWidth + childRightMargin;
                maxHeight = Math.max(maxHeight, childHeight + verticalMargin);
            }
        }
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
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    private void D(String msg) {
        Log.d(TAG, msg);
    }
}
