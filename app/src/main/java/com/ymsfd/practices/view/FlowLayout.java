package com.ymsfd.practices.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2015/11/10
 * Time: 16:18
 */
public class FlowLayout extends ViewGroup {
    private static int DEFAULT_VERTICAL_SPACING = 20;   //dp
    private static int DEFAULT_HORIZONTAL_SPACING = 20; //dp

    private final float mVerticalSpacing;
    private final float mHorizontalSpacing;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final float density = context.getResources().getDisplayMetrics().density;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
        mVerticalSpacing = array.getDimension(R.styleable.FlowLayout_verticalSpacing, DEFAULT_VERTICAL_SPACING * density + 0.5f);
        mHorizontalSpacing = array.getDimension(R.styleable.FlowLayout_horizontalSpacing, DEFAULT_HORIZONTAL_SPACING * density + 0.5f);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = resolveSize(0, widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int lineHeight = 0;
        float drawLeft = paddingLeft;
        float drawTop = paddingTop;
        int childHeight;
        int childWidth;
        int count = getChildCount();
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else {
                continue;
            }

            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            lineHeight = Math.max(lineHeight, childHeight);
            if (drawLeft + childWidth + paddingRight > measureWidth) {
                drawLeft = paddingLeft;
                drawTop += mVerticalSpacing + lineHeight;
                lineHeight = 0;
            } else {
                drawLeft += childWidth + mHorizontalSpacing;
            }
        }

        float wantedHeight = drawTop + lineHeight + paddingBottom;

        setMeasuredDimension(measureWidth, resolveSize((int) wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int lineHeight = 0;
        int drawLeft = paddingLeft;
        int drawTop = paddingTop;

        int childHeight;
        int childWidth;
        int count = getChildCount();
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            lineHeight = Math.max(lineHeight, childHeight);
            if (drawLeft + childWidth + paddingRight > width) {
                drawLeft = paddingLeft;
                drawTop += paddingTop + lineHeight + mVerticalSpacing;
                lineHeight = 0;
            }

            child.layout(drawLeft, drawTop, drawLeft + childWidth, drawTop + childHeight);
            drawLeft += childWidth + mHorizontalSpacing;
        }
    }
}
