package com.mouse.study.test.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author laiwufa
 * @date 2018/9/7
 * use:
 */
public class PerformaceHandler implements InvocationHandler {
    private Object target;
    public PerformaceHandler(Object target) {//①target为目标的业务类
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InterceptorService interceptorService = new InterceptorService();
        interceptorService.monitor();
        Object obj = method.invoke(target, args);//②通过反射方法调用目标业务类的业务方法
        return obj;
    }
}
