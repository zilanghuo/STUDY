package com.mouse.study.test.proxy;

import java.lang.reflect.Proxy;

/**
 * @author laiwufa
 * @date 2018/9/7
 * use:
 */
public class TestMain {

    public static void main(String[] args) {
        //①目标业务类
        ITakeService target = new TakeService();
        //② 将目标业务类和横切代码编织到一起
        PerformaceHandler handler = new PerformaceHandler(target);
        //③为编织了目标业务类逻辑和性能监视横切逻辑的handler创建代理类
        ITakeService proxy = (ITakeService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
        //④ 操作代理实例
        proxy.sayHello();
    }

}
