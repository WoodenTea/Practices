package com.ymsfd.practices.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2016/6/23
 * Time: 9:37
 */
public class LuckView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private int diameter;
    private int center;
    private int[] colors = new int[]{0xFFFED143, 0xFFFEF1C8};
    private Paint paint;
    private RectF rectF;
    private Bitmap[] bitmaps;
    private float sweepAngle;
    private float halfAngle;
    private int padding = 50;
    private Bitmap backgroundBitmap;
    private Bitmap pointerBitmap;
    private float offsetAngle = 0.f;
    private float speed;
    private boolean isRunning = false;
    private boolean isShouldEnd;

    public LuckView(Context context) {
        this(context, null);
    }

    public LuckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        holder = getHolder();
        holder.addCallback(this);

        bitmaps = new Bitmap[8];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_10);
        bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_20);
        bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_30);
        bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_40);
        bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_50);
        bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_60);
        bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_70);
        bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.gift_80);

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bet);
        pointerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_pointer);
        sweepAngle = 360.f / bitmaps.length;
        halfAngle = sweepAngle / 2.f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        diameter = width - getPaddingLeft() - getPaddingRight() - padding * 2;
        center = width / 2;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        rectF = new RectF(getPaddingLeft() + padding, getPaddingLeft() + padding, diameter
                + getPaddingLeft() + padding, diameter + getPaddingLeft() + padding);

        new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    long start = System.currentTimeMillis();
                    draw();
                    long end = System.currentTimeMillis();
                    try {
                        if (end - start < 80) {
                            Thread.sleep(80 - (end - start));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    private void D(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }

    public void callInExactArea(float startAngle) {
        // 让指针从水平向右开始计算
        float rotate = startAngle;
        rotate %= 360.0;
        for (int i = 0; i < bitmaps.length; i++) {
            // 每个的中奖范围
            float from = 360 - (i + 1) * (360 / bitmaps.length);
            float to = from + 360 - (i) * (360 / bitmaps.length);
            D("Index->" + i + " From->" + from + " To->" + to);
            if ((rotate > from) && (rotate < to)) {
                D("Index->" + i);
                return;
            }
        }
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        drawBackground(canvas);

        float startAngle = offsetAngle;
        canvas.save();
//        canvas.rotate(-halfAngle, center, center);
        for (int index = 0; index < bitmaps.length; index++) {
            int color = colors[index % 2];
            paint.setColor(color);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            drawIcon(canvas, startAngle, index);
            startAngle += sweepAngle;
        }
        offsetAngle += speed;
        if (isShouldEnd) {
            speed -= 1;
        }
        if (speed <= 0) {
            speed = 0;
            isShouldEnd = false;
        }
        canvas.restore();
        drawPointer(canvas);
        callInExactArea(offsetAngle);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawColor(0xFFFFFFFF);
        canvas.drawBitmap(backgroundBitmap, null,
                new Rect(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(),
                        getMeasuredHeight() - getPaddingBottom()), null);
    }

    private void drawIcon(Canvas canvas, float startAngle, int index) {
        int imageWidth = diameter / 8;

        float angle = (float) ((halfAngle + startAngle) * (Math.PI / 180));

        int x = (int) (center + diameter * 0.375 * Math.cos(angle));    //0.375 = 1/2 * 3/4
        int y = (int) (center + diameter * 0.375 * Math.sin(angle));
        Matrix matrix = new Matrix();
        matrix.postRotate(90 + halfAngle + startAngle);
        Bitmap bitmap = Bitmap.createBitmap(bitmaps[index], 0, 0, bitmaps[index].getWidth(),
                bitmaps[index].getHeight(), matrix, true);
        Rect rect = new Rect(x - imageWidth / 2, y - imageWidth / 2, x + imageWidth
                / 2, y + imageWidth / 2);

        canvas.drawBitmap(bitmap, null, rect, null);
    }

    private void drawPointer(Canvas canvas) {
        Rect rect = new Rect(center - pointerBitmap.getWidth() / 2,
                center - pointerBitmap.getHeight() / 2,
                center + pointerBitmap.getWidth() / 2,
                center + pointerBitmap.getHeight() / 2);
        canvas.drawBitmap(pointerBitmap, null, rect, null);
    }

    public void luckyEnd() {
        offsetAngle = 0;
        isShouldEnd = true;
    }

    public void luckyStart(int luckyIndex) {
        // 每项角度大小
        float angle = 360.f / bitmaps.length;
        // 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
        float from = 360 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // 停下来时旋转的距离
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        speed = (float) (v1 + Math.random() * (v2 - v1));

        D("Speed->" + speed);

        isShouldEnd = false;
    }
}
