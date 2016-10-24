package com.ymsfd.practices.infrastructure.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by  Deepan.
 * User: ymsfd
 * Date: 4/19/15
 * Time: 14:49
 */
public class DensityUtil {
    public static float px2dp(int value) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return value / density;
    }

    public static float px2sp(int value) {
        final float density = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return value / density;
    }

    public static float dp2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().getDisplayMetrics());
    }
}
