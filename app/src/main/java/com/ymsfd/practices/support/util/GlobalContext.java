package com.ymsfd.practices.support.util;

import android.app.Application;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/9/15
 * Time: 00:16
 */
public class GlobalContext extends Application {

    private static GlobalContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GlobalContext getInstance() {
        return instance;
    }
}
