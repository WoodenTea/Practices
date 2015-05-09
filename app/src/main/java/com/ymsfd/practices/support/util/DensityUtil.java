package com.ymsfd.practices.support.util;

import android.util.TypedValue;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 14:49
 */
public class DensityUtil {
    public static float px2dp(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, GlobalContext.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }

    public static float px2sp(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, GlobalContext.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }
}
