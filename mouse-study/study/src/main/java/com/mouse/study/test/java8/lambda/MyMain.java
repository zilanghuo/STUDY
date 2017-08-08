package com.mouse.study.test.java8.lambda;

/**
 * Created by lwf on 2017/8/7.
 * use to do:
 */
public class MyMain  {

    public static void main(String[] args) {
        MyInterface myInterface = ()->{return "hello";};
        MyInterface myInterfaceTwo = new MyInterface() {
            @Override
            public String sayHello() {
                return "good";
            }
        };
        System.out.println(myInterface.sayHello());
        System.out.println(myInterfaceTwo.sayHello());

    }
}
