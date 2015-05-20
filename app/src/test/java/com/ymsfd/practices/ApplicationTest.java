package com.ymsfd.practices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ApplicationTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Before");
    }

    @Test
    public void testSomething() throws Exception {
        System.out.println("Test");
    }
}