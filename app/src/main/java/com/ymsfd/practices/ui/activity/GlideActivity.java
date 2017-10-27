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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
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
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
        ImageView imageView3 = (ImageView) findViewById(R.id.image_view3);
        Glide.with(this).load("http://211.149.201.16/ecmall/data/files/mall/ad/5.png")
                .asBitmap()
                .into(imageView3);
        return true;
    }

    public Observable<String> createObservable(final String str) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(str);
            }
        });
    }
}
