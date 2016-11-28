package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
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
    final static int NUMBER = 4;
    private Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint floatPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mCentered;
    private boolean mSnap;
    private int mOrientation;
    private float mRadius;
    private int mTouchSlop;

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
        final int defaultRoundColor = ContextCompat.getColor(context,
                R.color.default_circle_indicator_round_color);
        final int defaultStrokeColor = ContextCompat.getColor(context,
                R.color.default_circle_indicator_stroke_color);
        final int defaultFloatColor = ContextCompat.getColor(context,
                R.color.default_circle_indicator_float_color);
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        final float defaultStrokeWidth = res.getDimension(R.dimen
                .default_circle_indicator_stroke_width);
        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
        final int defaultOrientation = res.getInteger(R.integer
                .default_circle_indicator_orientation);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicatorView,
                defStyleAttr, 0);
        mCentered = array.getBoolean(R.styleable.CircleIndicatorView_centered, defaultCentered);
        mOrientation = array.getInteger(R.styleable.CircleIndicatorView_orientation,
                defaultOrientation);
        mSnap = array.getBoolean(R.styleable.CircleIndicatorView_snap, defaultSnap);

        roundPaint.setColor(array.getColor(R.styleable.CircleIndicatorView_roundColor,
                defaultRoundColor));
        roundPaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(array.getColor(R.styleable.CircleIndicatorView_strokeColor,
                defaultStrokeColor));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(array.getDimension(R.styleable
                .CircleIndicatorView_strokeWidth, defaultStrokeWidth));
        floatPaint.setColor(array.getColor(R.styleable.CircleIndicatorView_floatColor,
                defaultFloatColor));
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

        int length;
        int width;
        int lengthPaddingBefore;
        int lengthPaddingAfter;
        int widthPaddingBefore;

        if (mOrientation == LinearLayout.HORIZONTAL) {
            length = getWidth();
            width = getHeight();
            lengthPaddingBefore = getPaddingLeft();
            lengthPaddingAfter = getPaddingRight();
            widthPaddingBefore = getPaddingTop();
            canvas.drawLine((float) length / 2, 0, (float) length / 2.0f, (float) width,
                    floatPaint);
        } else {
            length = getHeight();
            width = getWidth();
            lengthPaddingBefore = getPaddingTop();
            lengthPaddingAfter = getPaddingBottom();
            widthPaddingBefore = getPaddingLeft();
            canvas.drawLine(0, (float) length / 2, (float) width, (float) length / 2.0f,
                    floatPaint);
        }

        float lengthOffset = lengthPaddingBefore + mRadius;
        float widthOffset = widthPaddingBefore + mRadius;
        float threeRadius = 3 * mRadius;

        if (mCentered) {
            lengthOffset += ((length - lengthPaddingAfter - lengthPaddingAfter) / 2.0f) -
                    ((count * threeRadius) / 2.0f) + mRadius / 2.0f;
        }

        float dx;
        float dy;

        float roundRadius = mRadius;
        if (strokePaint.getStrokeWidth() > 0) {
            roundRadius -= strokePaint.getStrokeWidth() / 2.0f;
        }

        for (int index = 0; index < count; index++) {
            float drawLength = lengthOffset + index * threeRadius;
            if (mOrientation == LinearLayout.HORIZONTAL) {
                dx = drawLength;
                dy = widthOffset;
            } else {
                dx = widthOffset;
                dy = drawLength;
            }

            if (roundPaint.getAlpha() > 0) {
                canvas.drawCircle(dx, dy, roundRadius, roundPaint);
            }

            if (Math.abs(roundRadius - mRadius) < .0000001) {
                canvas.drawCircle(dx, dy, mRadius, strokePaint);
            }
        }

        if (mOrientation == LinearLayout.HORIZONTAL) {
            dx = lengthOffset;
            dy = widthOffset;
        } else {
            dx = widthOffset;
            dy = lengthOffset;
        }

        canvas.drawCircle(dx, dy, roundRadius, floatPaint);
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
            result = (int) (getPaddingLeft() + getPaddingRight() + NUMBER * mRadius + (NUMBER -
                    1) * mRadius);
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