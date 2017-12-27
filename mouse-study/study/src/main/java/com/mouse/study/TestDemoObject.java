package com.mouse.study;

/**
 * @author lwf
 * @date 2017/12/14
 * use: 面向对象错误
 */
public class TestDemoObject {

    @org.junit.Test
    public void testThree() {
        String s1 = new String("33");
        String s2 = s1;
        System.out.println(s1);
        System.out.println(s2);
        s2 = "22"; //相当于new String()
        System.out.println(s1);
        System.out.println(s2);
    }

    @org.junit.Test
    public void testTwo() {
        Object s1 = new Object();
        Object s2 = s1;
        System.out.println(s1);
        System.out.println(s2);

    }

    @org.junit.Test
    public void testOne() {
        float[][] f1 = {{1.2f, 2.3f}, {4.5f, 5.6f}};
        Object oo = f1;
        // f1[1] = oo; 向下转型，需要强制性
        System.out.println("Best Wishes " + f1[1]);
    }
}
