package com.ymsfd.practices;

import android.content.Intent;

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

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SceneActivityTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Before");
    }

    @Test
    public void clickJump() {
        SceneActivity activity = Robolectric.setupActivity(SceneActivity.class);
        activity.findViewById(R.id.cartoon).performClick();

        Intent expectedIntent = new Intent(activity, SceneActivity2.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(expectedIntent, actualIntent);
    }
}