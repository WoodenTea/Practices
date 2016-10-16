package com.ymsfd.practices.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2016/7/6
 * Time: 10:12
 */
public class RippleView extends View {

    private AnimatorSet animatorSet;
    private Paint paint;
    private Path path;
    private int width;
    private int height;
    private float x;
    private float y;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.ripple_color));

        path = new Path();

        animatorSet = new AnimatorSet();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(0, height);
        path.lineTo(0, height * 10 / 12);
        float x2 = width * 5 / 10;
        float y2 = height * 11 / 12;
        float x3 = width;
        float y3 = height / 2;
        path.cubicTo(x, y, x2, y2, x3, y3);
        path.lineTo(width, height);
        path.lineTo(0, height);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (heightSize * 0.75f);
        }

        setMeasuredDimension(width, height);
        startAnimator();
    }

    private void startAnimator() {
        ValueAnimator xAnimator = ValueAnimator.ofFloat(0, width * 3 / 10);
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator yAnimator = ValueAnimator.ofFloat(0, height * 11 / 12);
        yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animatorSet.setDuration(8000);
        animatorSet.playTogether(xAnimator, yAnimator);
        animatorSet.start();
    }
}
