package com.ymsfd.practices.infrastructure.util;

/**
 * Created by WoodenTea.
 * Date: 2016/5/26
 * Time: 11:06
 */
public class Preconditions {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }

        return reference;
    }
}
