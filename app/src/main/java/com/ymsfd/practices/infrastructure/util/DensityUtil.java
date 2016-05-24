package com.ymsfd.practices.infrastructure.util;

import android.content.Context;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 14:49
 */
public class DensityUtil {
    public static float px2dp(Context context, int value) {
        final float density = context.getResources().getDisplayMetrics().density;
        return value / density + .5f;
    }

    public static float dp2px(Context context, int value) {
        final float density = context.getResources().getDisplayMetrics().density;
        return value * density + .5f;
    }
}
