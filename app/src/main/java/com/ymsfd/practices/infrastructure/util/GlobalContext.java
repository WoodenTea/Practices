package com.ymsfd.practices.infrastructure.util;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/9/15
 * Time: 00:16
 */
public class GlobalContext extends Application {

    private static GlobalContext instance;

    public static GlobalContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
    }
}
