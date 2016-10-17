package com.ymsfd.practices.ui.activity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ymsfd.practices.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by WoodenTea.
 * Date: 2016/8/11
 * Time: 11:29
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivityTest {
    @Rule
    public ActivityTestRule<TestActivity> activityTestRule =
            new ActivityTestRule<>(TestActivity.class);

    @Test
    public void testClick() {
        onView(withId(R.id.submit)).perform(click());
    }
}