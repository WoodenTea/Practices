package com.google.zxing;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * Created by WoodenTea.
 * Date: 2016/6/12
 * Time: 17:20
 */
public class ResourceCompat {
    @ColorInt
    public static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(resId, context.getTheme());
        } else {
            return context.getResources().getColor(resId);
        }
    }
}
