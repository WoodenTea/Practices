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
    public static final int BACK = 0;
    public static final int FRONT = 1;

    @FACING
    public int facing;

    @IntDef({BACK, FRONT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FACING {
    }
}
