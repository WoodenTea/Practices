package com.ymsfd.practices.infrastructure.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by WoodenTea.
 * Date: 2016/7/7
 * Time: 16:28
 */
public class Utils {
    public static Point displaySize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Point point = new Point();
        point.set(metrics.widthPixels, metrics.heightPixels);
        return point;
    }
}
