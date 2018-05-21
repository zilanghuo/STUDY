package com.mouse.study.test.hutool;

import cn.hutool.core.util.ReflectUtil;
import com.mouse.study.test.es.rest.AnalyzeDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author lwf
 * @date 2018/5/18
 * use:
 */
public class ReflectUtilTest {


    @org.junit.Test
    public void newInstance(){
        String className = "com.mouse.study.test.es.rest.AnalyzeDemo";
        Object o = ReflectUtil.newInstance(className);
    }

    @org.junit.Test
    public void getConstructors(){
        Constructor<AnalyzeDemo>[] constructors = ReflectUtil.getConstructors(AnalyzeDemo.class);
        for (Constructor<AnalyzeDemo>  constructor : constructors){
            System.out.println(constructor.getName()+":"+constructor.getParameterCount());
        }

    }

    @org.junit.Test
    public void getMethods(){
        Method[] methods = ReflectUtil.getMethods(AnalyzeDemo.class);
        for (Method method : methods){
            System.out.println(method.getName()+":"+method.getDefaultValue());
        }
    }

}
