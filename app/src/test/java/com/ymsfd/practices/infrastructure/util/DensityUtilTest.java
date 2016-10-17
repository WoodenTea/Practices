package com.ymsfd.practices.infrastructure.util;

import android.os.Build;

import com.ymsfd.practices.BuildConfig;
import com.ymsfd.practices.ui.activity.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by WoodenTea.
 * Date: 2016/8/11
 * Time: 11:19
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class DensityUtilTest {

    @Test
    public void testPx2dp() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        System.out.println(DensityUtil.px2dp(activity, 3));
    }
}