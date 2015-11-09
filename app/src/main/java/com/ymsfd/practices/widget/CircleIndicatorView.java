package com.ymsfd.practices.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
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
public class CircleIndicatorView extends View {
    private Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint floatPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mCentered;
    private boolean mSnap;
    private int mOrientation;
    private float mRadius;
    private int mTouchSlop;
    final static int NUMBER = 4;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }

        final Resources res = context.getResources();
        final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
        final int defaultRoundColor = res.getColor(R.color.default_circle_indicator_round_color);
        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
        final int defaultFloatColor = res.getColor(R.color.default_circle_indicator_float_color);
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
        final int defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView, defStyleAttr, 0);
        mCentered = array.getBoolean(R.styleable.CircleIndicatorView_centered, defaultCentered);
        mOrientation = array.getInteger(R.styleable.CircleIndicatorView_android_orientation, defaultOrientation);
        mSnap = array.getBoolean(R.styleable.CircleIndicatorView_snap, defaultSnap);

        roundPaint.setColor(array.getColor(R.styleable.CircleIndicatorView_roundColor, defaultRoundColor));
        roundPaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(array.getColor(R.styleable.CircleIndicatorView_strokeColor, defaultStrokeColor));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(array.getDimension(R.styleable.CircleIndicatorView_strokeWidth, defaultStrokeWidth));
        floatPaint.setColor(array.getColor(R.styleable.CircleIndicatorView_floatColor, defaultFloatColor));
        floatPaint.setStyle(Paint.Style.FILL);

        mRadius = array.getDimension(R.styleable.CircleIndicatorView_radius, defaultRadius);

        array.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int count = NUMBER;
        if (count == 0) {
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
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * threeRadius) / 2.0f);
        }

        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (strokePaint.getStrokeWidth() > 0) {
            pageFillRadius -= strokePaint.getStrokeWidth() / 2.0f;
        }

        //Draw stroked circles
        for (int iLoop = 0; iLoop < count; iLoop++) {
            float drawLong = longOffset + (iLoop * threeRadius);
            if (mOrientation == LinearLayout.HORIZONTAL) {
                dX = drawLong;
                dY = shortOffset;
            } else {
                dX = shortOffset;
                dY = drawLong;
            }
            // Only paint fill if not completely transparent
            if (roundPaint.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, roundPaint);
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, strokePaint);
            }
        }

        //Draw the filled circle according to the current scroll
        float cx = 0 * threeRadius;

        if (mOrientation == LinearLayout.HORIZONTAL) {
            dX = longOffset + cx;
            dY = shortOffset;
        } else {
            dX = shortOffset;
            dY = longOffset + cx;
        }
        canvas.drawCircle(dX, dY, mRadius, floatPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == LinearLayout.HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    private int measureLong(int measureSpec) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (getPaddingLeft() + getPaddingRight() + NUMBER * mRadius + (NUMBER - 1) * mRadius);
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
            result = (int) (getPaddingBottom() + getPaddingTop() + 2 * mRadius);
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
