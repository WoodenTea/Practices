package com.ymsfd.practices;

import com.ymsfd.practices.infrastructure.util.IdWorker;

import org.junit.Test;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/3/29
 */
public class PracticesUnitTest {
    @Test
    public void a() throws Exception {
        IdWorker worker = new IdWorker(8, 12);
        Long id = worker.nextId();
        System.out.println(id);

        System.out.println(id >>> 22);
        System.out.println(id >>> 12 << 59 >>> 59);
        System.out.println(id >>> 17 << 59 >>> 59);
        System.out.println(id << 52 >>> 52);
    }
}
