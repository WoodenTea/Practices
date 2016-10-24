package com.ymsfd.practices.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.ymsfd.practices.R;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by WoodenTea.
 * Date: 2015/12/9
 * Time: 17:01
 */
public class GlideActivity extends BaseActivity {
    ImageView imageView;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.glide_activity);
        enableToolbarHomeButton(true);
        imageView = (ImageView) findViewById(R.id.imageView);
        createObservable("http://testecshop2.magicwe.com/" +
                "images/201509/source_img/793_G_1441785900196.jpg")
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        File file = null;
                        try {
                            FutureTarget<File> futureTarget = Glide.with(GlideActivity.this).load
                                    (s).downloadOnly(100, 100);
                            file = futureTarget.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(File file) {
                    }
                });

        Glide.with(this).load("http://5.26923" +
                ".com/download/pic/000/263/ff5bc9a1c0386778a0ba1a783fd0fa2c.jpg").asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap>
                            target, boolean isFromMemoryCache, boolean isFirstResource) {
                        D("ResourceReady");
                        return false;
                    }
                })
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create
                                (getResources(), resource);
                        drawable.setCornerRadius(20.0f);
                        drawable.setCircular(true);
                        drawable.setAntiAlias(true);
                        view.setImageDrawable(drawable);
                    }
                });

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(imageView2);
        Glide.with(this).load("http://pic.joke01.com/uppic/13-05/30/30215236.gif").placeholder(R
                .drawable.cartoon).error(R.drawable.border_circle).into(target);

        return true;
    }

    public Observable<String> createObservable(final String str) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(str);
            }
        });
    }
}
