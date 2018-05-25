package com.mouse.study.test.java8.reflect;

/**
 * @author lwf
 * @date 2018/5/25
 * use:
 */
public class CollUtils {

    public String sayHello(String user) {
        return "hello," + user;
    }

    public static String staticMethod(String name) {
        return "static name:" + name;
    }

    private String privateMethod(String name) {
        return "private name:" + name;
    }

}
