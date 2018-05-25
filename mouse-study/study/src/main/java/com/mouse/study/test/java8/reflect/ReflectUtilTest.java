package com.mouse.study.test.java8.reflect;

import java.lang.reflect.Method;

/**
 * @author lwf
 * @date 2018/5/25
 * use:
 */
public class ReflectUtilTest {


    @org.junit.Test
    public void invokerMethod() throws Exception {
        //创建一个对象
        String className = "com.mouse.study.test.java8.reflect.CollUtils";
        Object object = Class.forName(className).newInstance();
        System.out.println(object);
        // 获取方法
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
            //执行方法
            if (method.getName().equals("privateMethod")){
                method.setAccessible(true);
            }
            Object returnValue = method.invoke(object, "小明");
            System.out.println("返回值：" + returnValue);
        }
    }
}
