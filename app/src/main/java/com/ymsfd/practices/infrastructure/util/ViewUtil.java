package com.ymsfd.practices.infrastructure.util;

import android.view.View;

/**
 * Created by WoodenTea.
 * Date: 2016/5/26
 * Time: 11:06
 */
public class ViewUtil {
    public static void checkViewIsNull(View view) {
        if (view == null) {
            throw new NullPointerException("The view is null. Do you forget the view's id?");
        }
    }
}
