package com.ymsfd.practices.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 13:46
 */
public class CustomView extends View {
    private Paint strokePaint;
    private Paint fillPaint;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(6);
        strokePaint.setColor(Color.BLUE);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.RED);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(50, 50, 40.f, strokePaint);
        canvas.drawCircle(50, 50, 37f, fillPaint);

    }

    private void D(String msg) {
        Log.d("CustomView", msg);
    }
}
