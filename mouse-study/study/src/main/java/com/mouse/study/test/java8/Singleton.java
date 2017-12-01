package com.mouse.study.test.java8;

/**
 * @author lwf
 * @date 2017/11/29
 * use:
 */
public class Singleton {

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        int num = 50 ;
        num = num ++ * 2 ; // num = (num * 2)++
        System.out.println(num) ;
        System.out.println(num) ;
    }


}
