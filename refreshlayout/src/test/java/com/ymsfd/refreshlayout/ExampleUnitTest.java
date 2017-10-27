package com.ymsfd.refreshlayout;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        Random random = new Random(1506960000);
        int m = 300;
        Long seed = 150696000L;
        for (int j = 0; j < 24; j++) {
            long x = seed + m * j;
            for (int i = 0; i < 10; i++) {
                Random random = new Random(x + i);
                System.out.println(j + " " + i + " " + random.nextInt(10));
            }
        }
    }
}