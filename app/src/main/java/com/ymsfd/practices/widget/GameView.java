package com.ymsfd.practices.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by WoodenTea.
 * Date: 2015/12/21
 * Time: 14:03
 */
public class GameView extends View {
    private Paint mPaint;
    private Paint paint;
    private RectF rectF, arcRectF;
    private PorterDuffXfermode xfermode;
    private CountDownTimer downTimer;
    private long millisInFuture, countDownInterval;
    private float arc, step;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        millisInFuture = 8000;
        countDownInterval = 200;
        step = 9.f;
        arc = step;

        rectF = new RectF(10, 10, 90, 140);
        arcRectF = new RectF(-100, -75, 200, 225);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        if (isInEditMode()) {
            return;
        }

        downTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                invalidate();
                arc += step;
            }

            @Override
            public void onFinish() {
                System.out.println("Done");
            }
        };
        downTimer.start();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        if (arc > 360)
            arc = 0;

        if (arc > 180) {
            mPaint.setColor(Color.BLUE);
        } else {
            mPaint.setColor(Color.GREEN);
        }

        canvas.drawRoundRect(rectF, 10, 10, mPaint);
        mPaint.setXfermode(xfermode);

        canvas.drawArc(arcRectF, 240, arc, true, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (downTimer != null) {
            downTimer.cancel();
        }
    }

}
