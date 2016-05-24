package com.ymsfd.practices.ui.widget.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by WoodenTea.
 * Date: 2016/5/10
 * Time: 17:07
 */
public class ScrollDirection {
    @Direction
    public static final int NORMAL = 0;
    @Direction
    public static final int UP = 1;
    @Direction
    static final int DOWN = 2;
    @Direction
    public int direction = NORMAL;

    @Direction
    public int getDirection() {
        return direction;
    }

    public void setDirection(@Direction int direction) {
        this.direction = direction;
    }

    @IntDef({NORMAL, UP, DOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }
}
