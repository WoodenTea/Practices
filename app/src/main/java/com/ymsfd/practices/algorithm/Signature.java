package com.ymsfd.practices.algorithm;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/5/16
 */
public class Signature {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    public static native String encode(String str);
}