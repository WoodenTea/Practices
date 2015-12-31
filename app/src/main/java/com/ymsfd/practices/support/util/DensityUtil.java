package com.ymsfd.practices.support.util;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 14:49
 */
public class DensityUtil {
    public static float px2dp(int value) {
        final float density = GlobalContext.getInstance().getResources().getDisplayMetrics()
                .density;
        return value / density + .5f;
    }

    public static float dp2px(int value) {
        final float density = GlobalContext.getInstance().getResources().getDisplayMetrics()
                .density;
        return value * density + .5f;
    }
}
