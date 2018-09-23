package com.mouse.study.test.proxy.reflectProxy;

import java.lang.reflect.Constructor;

/**
 * @author laiwufa
 * @date 2018/9/23
 * use:
 */
public class TestMain {

    public static void main(String[] args) throws Exception{
        Class<IPutService> classz = (Class<IPutService>)Class.forName("com.mouse.study.test.proxy.reflectProxy.IPutService");
        Constructor<IPutService> con = classz.getConstructor();
        IPutService putService =  con.newInstance();

    }
}
