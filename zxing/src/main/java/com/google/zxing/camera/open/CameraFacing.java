package com.google.zxing.camera.open;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by WoodenTea.
 * Date: 2016/6/3
 * Time: 11:44
 */
public class CameraFacing {
    @FACING
    public static final int BACK = 0;
    @FACING
    public static final int FRONT = 1;

    @FACING
    public int facing;

    @FACING
    public static int parse(int value) {
        if (value == BACK) {
            return BACK;
        } else if (value == FRONT) {
            return FRONT;
        }

        throw new IllegalArgumentException("No value constant " +
                CameraFacing.class.getName() + "." + value);
    }

    @IntDef({BACK, FRONT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FACING {
    }
}
