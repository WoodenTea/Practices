package com.ymsfd.practices.infrastructure.util;

import android.util.Log;

import com.ymsfd.practices.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by WoodenTea.
 * Date: 2016/7/6
 * Time: 14:37
 */
public class WTLogger {
    public static void d(String tag, String format, Object... args) {
        if (BuildConfig.DEBUG) {
            d(tag, String.format(format, args));
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, Throwable e) {
        if (BuildConfig.DEBUG) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            e(tag, sw.toString());
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (BuildConfig.DEBUG) {
            e(tag, String.format(format, args));
        }
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
