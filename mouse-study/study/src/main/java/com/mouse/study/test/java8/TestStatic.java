package com.mouse.study.test.java8;

import com.mouse.study.model.MsgMessage;

import java.lang.reflect.Field;

/**
 * Created by lwf on 2017/8/18.
 * use to do:
 */
public class TestStatic {

    static {
        System.out.println("hellowor ");
    }

    private static TestStatic instance = new TestStatic();

    public static TestStatic getInsatnce() {
        return instance;
    }

    private TestStatic() {

    }

    public static void main(String[] args) {
        new TestStatic().testFiled();
    }


    public void testFiled() {
        Field[] declaredFields = MsgMessage.class.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }

    }
}
