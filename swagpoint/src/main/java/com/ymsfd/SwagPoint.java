package com.ymsfd;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ymsfd.swagpoint.R;

public class SwagPoint extends View {

    private final int ANGLE_OFFSET = -90;
    private final int MAX = 100;
    private final int MIN = 0;
    private final int INVALID_VALUE = -1;

    private int mPoint;
    private int mMax = MAX;
    private int mMin = MIN;

    private Drawable mIndicatorIcon;

    private float mProgressWidth;
    private int mProgressColor;
    private float mProgressSweep;
    private Paint mProgressPaint;

    private Paint mArcPaint;
    private RectF mArcRectF = new RectF();
    private float mArcRadius;
    private float mArcWidth;
    private int mArcColor;

    private Paint mTextPaint;
    private Rect mTextRect = new Rect();
    private float mTextSize;
    private int mTextColor;

    private boolean mClockwise = true;
    private boolean mEnabled = true;

    private float mTranslateY;
    private float mTranslateX;

    private float mIndicatorIconX;
    private float mIndicatorIconY;
    private float mReserve;
    private int mViewWidth;

    private int mUpdateTimes = 0;
    private float mPreviousProgress = -1;
    private float mCurrentProgress = 0;

    private boolean isMax = false;
    private boolean isMin = false;

    public SwagPoint(Context context) {
        super(context);
        init(context, null);
    }

    public SwagPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwagPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setPressed(true);
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    setPressed(false);
                    break;
            }

            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mClockwise) {
            canvas.scale(-1, -1, mArcRectF.centerX(), mArcRectF.centerY());
        }

        String textPoint = String.valueOf(mPoint);
        mTextPaint.getTextBounds(textPoint, 0, textPoint.length(), mTextRect);
        float xPos = mViewWidth * .5f - mTextRect.width() * .5f;
        float yPos = mArcRectF.centerY() - ((mTextPaint.descent() + mTextPaint.ascent()) * 0.5f);
        canvas.drawText(textPoint, xPos, yPos, mTextPaint);

        canvas.drawArc(mArcRectF, ANGLE_OFFSET, 360, false, mArcPaint);
        canvas.drawArc(mArcRectF, ANGLE_OFFSET, mProgressSweep, false, mProgressPaint);

        if (mEnabled) {
            canvas.translate(mTranslateX - mIndicatorIconX, mTranslateY - mIndicatorIconY);
            mIndicatorIcon.draw(canvas);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mIndicatorIcon != null && mIndicatorIcon.isStateful()) {
            int[] state = getDrawableState();
            mIndicatorIcon.setState(state);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = getDefaultSize(getMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getMinimumHeight(), heightMeasureSpec);
        D("Width->" + mViewWidth + " Height->" + height);
        final int min = Math.min(mViewWidth, height);

        mTranslateX = mViewWidth * 0.5f;
        mTranslateY = height * 0.5f;
        int arcDiameter = min - getPaddingLeft();
        mArcRadius = arcDiameter * 0.5f;
        float top = height * 0.5f - (arcDiameter * 0.5f);
        float left = mViewWidth * 0.5f - (arcDiameter * 0.5f);
        mArcRectF.set(left + mReserve,
                top + mReserve,
                left + arcDiameter - mReserve,
                top + arcDiameter - mReserve);

        updateIndicatorIconPosition();

        setMeasuredDimension(mViewWidth, height);
    }

    private void updatePoint(int progress) {
        updateProgress(progress);
    }

    private void updateProgress(int progress) {
        // detect points change closed to max or min
        final int maxDetectValue = (int) ((double) mMax * 0.95);
        final int minDetectValue = (int) ((double) mMax * 0.05) + mMin;
        mUpdateTimes++;
        if (progress == INVALID_VALUE) {
            return;
        }

        // avoid accidentally touch to become max from original point
        if (progress > maxDetectValue && mPreviousProgress == INVALID_VALUE) {
            return;
        }

        // record previous and current progress change
        if (mUpdateTimes == 1) {
            mCurrentProgress = progress;
        } else {
            mPreviousProgress = mCurrentProgress;
            mCurrentProgress = progress;
        }

        /**
         * Determine whether reach max or min to lock point update event.
         *
         * When reaching max, the progress will drop from max (or maxDetectPoints ~ max
         * to min (or min ~ minDetectPoints) and vice versa.
         *
         * If reach max or min, stop increasing / decreasing to avoid exceeding the max / min.
         */
        if (mUpdateTimes > 1 && !isMin && !isMax) {
            if (mPreviousProgress >= maxDetectValue
                    && mCurrentProgress <= minDetectValue
                    && mPreviousProgress > mCurrentProgress) {
                isMax = true;
                progress = mMax;
                updateDraw(progress);
            } else if (mCurrentProgress >= maxDetectValue
                    && mPreviousProgress <= minDetectValue
                    && mCurrentProgress > mPreviousProgress) {
                isMin = true;
                progress = mMin;
                updateDraw(progress);
            }
        }

        // Detect whether decreasing from max or increasing from min, to unlock the update event.
        // Make sure to check in detect range only.
        if (isMax & (mCurrentProgress < mPreviousProgress)
                && mCurrentProgress >= maxDetectValue) {
            isMax = false;
        }

        if (isMin && (mPreviousProgress < mCurrentProgress)
                && mPreviousProgress <= minDetectValue
                && mCurrentProgress <= minDetectValue) {
            isMin = false;
        }

        if (!isMax && !isMin) {
            updateDraw(progress);
        }
    }

    private void updateDraw(int progress) {
        progress = (progress > mMax) ? mMax : progress;
        progress = (progress < mMin) ? mMin : progress;
        mPoint = progress;

        mProgressSweep = (float) progress / mMax * 360;
        updateIndicatorIconPosition();
        invalidate();
    }

    private void updateOnTouch(MotionEvent event) {
        double touchAngle = convertTouchEventPointToAngle(event.getX(), event.getY());
        int progress = convertAngleToProgress(touchAngle);
        updatePoint(progress);
    }

    private double convertTouchEventPointToAngle(float xPos, float yPos) {
        // transform touch coordinate into component coordinate
        float x = xPos - mTranslateX;
        float y = yPos - mTranslateY;

        x = (mClockwise) ? x : -x;
        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2));
        angle = (angle < 0) ? (angle + 360) : angle;
        return angle;
    }

    private float valuePerDegree() {
        return (float) (mMax - mMin) / 360.f;
    }

    private int convertAngleToProgress(double angle) {
        int touchProgress = (int) Math.round(valuePerDegree() * angle);
        touchProgress = (touchProgress < mMin) ? INVALID_VALUE : touchProgress;
        touchProgress = (touchProgress > mMax) ? INVALID_VALUE : touchProgress;
        return touchProgress;
    }

    private void updateIndicatorIconPosition() {
        int thumbAngle = (int) (mProgressSweep + Math.abs(ANGLE_OFFSET));
        mIndicatorIconX = (mArcRadius - mReserve) * (float) Math.cos(Math.toRadians(thumbAngle));
        mIndicatorIconY = (mArcRadius - mReserve) * (float) Math.sin(Math.toRadians(thumbAngle));
    }

    void D(String message) {
        Log.d("SwagPoint", message);
    }

    private void init(Context context, AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;

        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwagPoint,
                    0, 0);

            mIndicatorIcon = array.getDrawable(R.styleable.SwagPoint_indicatorIcon);
            if (mIndicatorIcon == null) {
                mIndicatorIcon = ContextCompat.getDrawable(context, R.drawable.ic_indicator);
            }
            int indicatorIconHalfWidth = (int) (mIndicatorIcon.getIntrinsicWidth() * 0.5f);
            int indicatorIconHalfHeight = (int) (mIndicatorIcon.getIntrinsicHeight() * 0.5f);
            mIndicatorIcon.setBounds(-indicatorIconHalfWidth, -indicatorIconHalfHeight,
                    indicatorIconHalfWidth, indicatorIconHalfHeight);

            mPoint = array.getInteger(R.styleable.SwagPoint_point, MIN);
            mMin = array.getInteger(R.styleable.SwagPoint_min, MIN);
            mMax = array.getInteger(R.styleable.SwagPoint_max, MAX);

            mProgressWidth = array.getDimension(R.styleable.SwagPoint_progressWidth, 12 * density);
            mProgressColor = array.getColor(R.styleable.SwagPoint_progressColor,
                    ContextCompat.getColor(context, R.color.colorProgress));

            mArcWidth = array.getDimension(R.styleable.SwagPoint_arcWidth, 14 * density);
            mArcColor = array.getColor(R.styleable.SwagPoint_arcColor,
                    ContextCompat.getColor(context, R.color.colorArc));

            mTextSize = array.getDimension(R.styleable.SwagPoint_textSize, 12);
            mTextColor = array.getColor(R.styleable.SwagPoint_textColor,
                    ContextCompat.getColor(context, R.color.colorText));

            float halfStroke = Math.max(mProgressWidth, mArcWidth);
            halfStroke *= .5f;

            mReserve = halfStroke + indicatorIconHalfHeight;

            mClockwise = array.getBoolean(R.styleable.SwagPoint_clockwise, true);
            mEnabled = array.getBoolean(R.styleable.SwagPoint_enabled, true);
            array.recycle();
        }

        mPoint = mPoint > mMax ? mMax : mPoint;
        mPoint = mPoint < mMin ? mMin : mPoint;

        mProgressSweep = (float) mPoint / mMax * 360.f;

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
    }
}
