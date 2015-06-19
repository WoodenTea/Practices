package com.ymsfd.practices.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 13:46
 */
public class CircleIndicator extends View {
    private Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint pagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mCentered;
    private int mOrientation = 0;
    private float mRadius = 0;
    private boolean mSnap;
    private int mTouchSlop;
    final int mCount = 3;

    public CircleIndicator(Context context) {
        this(context, null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }

        final Resources res = getResources();
        final int defaultPageColor = res
                .getColor(R.color.default_circle_indicator_page_color);
        final int defaultFillColor = res
                .getColor(R.color.default_circle_indicator_fill_color);
        final int defaultOrientation = res
                .getInteger(R.integer.default_circle_indicator_orientation);
        final int defaultStrokeColor = res
                .getColor(R.color.default_circle_indicator_stroke_color);
        final float defaultStrokeWidth = res
                .getDimension(R.dimen.default_circle_indicator_stroke_width);
        final float defaultRadius = res
                .getDimension(R.dimen.default_circle_indicator_radius);
        final boolean defaultCentered = res
                .getBoolean(R.bool.default_circle_indicator_centered);
        final boolean defaultSnap = res
                .getBoolean(R.bool.default_circle_indicator_snap);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CircleIndicator, defStyleAttr, 0);
        mCentered = a.getBoolean(R.styleable.CircleIndicator_centered,
                defaultCentered);
        mOrientation = a.getInt(
                R.styleable.CircleIndicator_android_orientation,
                defaultOrientation);
        mRadius = a.getDimension(R.styleable.CircleIndicator_radius,
                defaultRadius);
        mSnap = a.getBoolean(R.styleable.CircleIndicator_snap, defaultSnap);

        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(defaultStrokeWidth);
        strokePaint.setColor(defaultStrokeColor);

        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(defaultFillColor);

        pagePaint.setStyle(Paint.Style.FILL);
        pagePaint.setColor(defaultPageColor);

        a.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat
                .getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCount == 0) {
            return;
        }

        int longSize;
        int longPaddingBefore;
        int longPaddingAfter;
        int shortPaddingBefore;
        if (mOrientation == LinearLayout.HORIZONTAL) {
            longSize = getWidth();
            longPaddingBefore = getPaddingLeft();
            longPaddingAfter = getPaddingRight();
            shortPaddingBefore = getPaddingTop();
        } else {
            longSize = getHeight();
            longPaddingBefore = getPaddingTop();
            longPaddingAfter = getPaddingBottom();
            shortPaddingBefore = getPaddingLeft();
        }

        final float threeRadius = mRadius * 3;
        final float shortOffset = shortPaddingBefore + mRadius;
        float longOffset = longPaddingBefore + mRadius;
        if (mCentered) {
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f)
                    - ((mCount * threeRadius) / 2.0f);
        }

        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (strokePaint.getStrokeWidth() > 0) {
            pageFillRadius -= strokePaint.getStrokeWidth() / 2.0f;
        }

        // Draw stroked circles
        for (int index = 0; index < mCount; index++) {
            float drawLong = longOffset + (index * threeRadius);
            if (mOrientation == LinearLayout.HORIZONTAL) {
                dX = drawLong;
                dY = shortOffset;
            } else {
                dX = shortOffset;
                dY = drawLong;
            }

            // Only paint fill if not completely transparent
            if (pagePaint.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, pagePaint);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, strokePaint);
            }
        }

        // Draw the filled circle according to the current scroll
        float cx = 0.0f;

        if (mOrientation == LinearLayout.HORIZONTAL) {
            dX = longOffset + cx;
            dY = shortOffset;
        } else {
            dX = shortOffset;
            dY = longOffset + cx;
        }

        canvas.drawCircle(dX, dY, mRadius, fillPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == LinearLayout.HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec),
                    measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec),
                    measureLong(heightMeasureSpec));
        }
    }

    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY)) {
            result = specSize;
        } else {

            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (mCount * 2 * mRadius) + (mCount - 1) * mRadius + 1);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private void D(String msg) {
        Log.d("CustomView", msg);
    }
}
