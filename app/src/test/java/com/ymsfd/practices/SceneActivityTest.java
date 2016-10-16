package com.ymsfd.practices;

import android.content.Intent;

import com.google.zxing.camera.FrontLightMode;
import com.google.zxing.camera.open.CameraFacing;
import com.ymsfd.practices.ui.activity.SceneActivity;
import com.ymsfd.practices.ui.activity.SceneActivity2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, constants = BuildConfig.class, sdk = 21)
public class SceneActivityTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Before");
    }

    @Test
    public void testClickJump() {
        SceneActivity activity = Robolectric.setupActivity(SceneActivity.class);
        activity.findViewById(R.id.cartoon).performClick();

        Intent expectedIntent = new Intent(activity, SceneActivity2.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(expectedIntent, actualIntent);
    }

    @Test
    public void testFormat() {
        Long timestamp = 1476263113 * 1000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        System.out.println(format.format(date));
        System.out.println(String.format("时-> %tH", date));
        System.out.println(String.format("分-> %tM", date));
        System.out.println(String.format(Locale.getDefault(), "%td", date));
    }
}