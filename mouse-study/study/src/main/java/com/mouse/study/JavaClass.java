package com.mouse.study;

import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by lwf on 2017/10/12.
 * use to do:
 */
public class JavaClass implements Serializable {
    private int age = 5;
    public  int high = 100;
    public static final String type = "chinese";//(1)

    public static void main(String[] args) throws Exception {
        Method m = ObjectStreamClass.class.getDeclaredMethod("computeDefaultSUID", new Class[]{Class.class});
        m.setAccessible(true);
        Long value = (Long)m.invoke(ObjectStreamClass.class, new Class[]{JavaClass.class});
        System.out.println(value.longValue());
    }
}