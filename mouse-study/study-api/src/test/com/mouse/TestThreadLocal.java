package com.mouse;

/**
 * Created by lwf on 2017/6/12.
 */
public class TestThreadLocal {

    public static void main(String[] args) {


    }

    @org.junit.Test
    public void testInit() {
        ThreadLocal<String> tls = new ThreadLocal() {
            @Override
            protected String initialValue() {
                return Thread.currentThread().getName();
            }
        };
        System.out.println(tls.get());
    }
}
