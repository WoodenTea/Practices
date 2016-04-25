package com.ymsfd.practices.ui.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 5/20/15
 * Time: 13:03
 */
public class SceneActivityTest extends ActivityInstrumentationTestCase2<SceneActivity> {
    private Solo solo;

    public SceneActivityTest() {
        super(SceneActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testClick() throws Exception {
        solo.clickOnImage(0);
    }
}