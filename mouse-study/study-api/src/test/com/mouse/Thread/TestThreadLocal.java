package com.mouse.Thread;

/**
 * Created by lwf on 2017/6/12.
 * use to: test ThreadLocal class
 */
public class TestThreadLocal {

    @org.junit.Test
    public void testInit() {
        ThreadLocal<String> tls = new ThreadLocal() {
            @Override
            protected String initialValue() {
                Thread.currentThread().setName("testInit");
                return Thread.currentThread().getName();
            }
        };
        System.out.println(tls.get());
    }
}
