package com.mouse.resource.list;

/**
 * Created by lwf on 2017/7/17.
 * use to do:
 */
public class StringDemo {

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        String a = "a" + "b" + 1;
        String b = "ab1";
        System.out.println(a == b);
    }

    public static void test2() {
        String a = "a";
        final String c = "a";
        String b = a + "b";
        String d = c + "b";
        String e = getA() + "b";
        String compare = "ab";
        System.out.println(b == compare);
        System.out.println(d == compare);
        System.out.println(e == compare);
    }

    private static String getA() {
        return "a";
    }

}
