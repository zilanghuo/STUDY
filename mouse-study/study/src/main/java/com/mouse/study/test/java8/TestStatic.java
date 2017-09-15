package com.mouse.study.test.java8;

import com.mouse.study.model.MsgMessage;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        Long time = 1505360220142L;
        String d = format.format(time);
        Date date = format.parse(d);
        System.out.println("Format To String(Date):" + d);
        System.out.println("Format To Date:" + date);
    }


    public void testFiled() {
        Field[] declaredFields = MsgMessage.class.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }

    }
}
