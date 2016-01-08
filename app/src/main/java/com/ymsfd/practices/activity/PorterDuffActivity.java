package com.ymsfd.practices.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2015/12/21
 * Time: 10:41
 */
public class PorterDuffActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_porterduff);
        ImageView image = (ImageView) findViewById(R.id.image);
        Bitmap bitmapBorder;
        Bitmap bitmapMask;
        Paint paint;
        PorterDuffXfermode xfermode;

        bitmapBorder = decodeBitmap(R.drawable.border_circle);
        bitmapMask = decodeBitmap(R.drawable.mask_circle);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Bitmap bitmap = Bitmap.createBitmap(bitmapBorder.getWidth(), bitmapBorder.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmapBorder, 0, 0, paint);
        int saveFlags = Canvas.ALL_SAVE_FLAG;
        canvas.saveLayer(0, 0, bitmapBorder.getWidth(), bitmapBorder.getHeight(), null, saveFlags);
        canvas.drawBitmap(bitmapMask, 0, 0, paint);
        paint.setXfermode(xfermode);
        Bitmap b = decodeBitmap(R.drawable.cartoon);
        int left = bitmapMask.getWidth() / 2 - b.getWidth() / 2;
        int top = bitmapMask.getHeight() / 2 - b.getHeight() / 2;
        canvas.drawBitmap(b, left, top, paint);
        canvas.restore();
        image.setImageBitmap(bitmap);

        image = (ImageView) findViewById(R.id.image2);
        int color = 0xFF45C01A;
        Rect rect = new Rect(b.getWidth() / 4, b.getHeight() / 4, b.getWidth() * 3 / 4, b
                .getHeight() * 3 / 4);

        bitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas = new Canvas(bitmap);
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.saveLayer(0, 0, b.getWidth(), b.getHeight(), null, saveFlags);
        paint.setColor(color);
        paint.setDither(true);
        paint.setAlpha(100);
        canvas.drawRect(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAlpha(255);
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.restore();
        image.setImageBitmap(bitmap);

        image = (ImageView) findViewById(R.id.image3);
        bitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        rect = new Rect(b.getWidth() / 4, b.getHeight() / 4, b.getWidth() * 3 / 4, b.getHeight()
                * 3 / 4);

        canvas.drawRect(rect, paint);
        canvas.save();
        rect = new Rect(b.getWidth() / 3, b.getHeight() / 3, b.getWidth() * 2 / 3, b.getHeight()
                * 2 / 3);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);
        canvas.restore();
        image.setImageBitmap(bitmap);

        return true;
    }

    private Bitmap decodeBitmap(int id) {
        return BitmapFactory.decodeResource(getResources(), id);
    }
}
