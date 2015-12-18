package com.ymsfd.practices.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * Date: 2015/12/9
 * Time: 17:01
 */
public class GlideActivity extends BaseActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_glide);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //Glide.with(this).load("http://5.26923.com/download/pic/000/263/ff5bc9a1c0386778a0ba1a783fd0fa2c.jpg").into(imageView);
        Glide.with(this).load("http://5.26923.com/download/pic/000/263/ff5bc9a1c0386778a0ba1a783fd0fa2c.jpg").asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                drawable.setCornerRadius(20.0f);
                drawable.setCircular(true);
                drawable.setAntiAlias(true);
                view.setImageDrawable(drawable);
            }
        });

        return true;
    }
}
