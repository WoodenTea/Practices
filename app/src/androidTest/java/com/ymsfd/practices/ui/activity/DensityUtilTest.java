package com.ymsfd.practices.ui.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.ymsfd.practices.infrastructure.util.DensityUtil;

/**
 * Created by WoodenTea.
 * Date: 2015/12/28
 * Time: 11:56
 */
public class DensityUtilTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public DensityUtilTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testDensity() throws Exception {
        assertEquals("px2dp", 3.5f, DensityUtil.px2dp(9));
        assertEquals("dp2px", 3.0f, DensityUtil.dp2px(2));
    }
}
