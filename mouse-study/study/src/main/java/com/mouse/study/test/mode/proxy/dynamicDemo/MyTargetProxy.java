package com.mouse.study.test.mode.proxy.dynamicDemo;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lwf
 * @date 2018/1/2
 * use: 拦截器带参数
 */
@Data
public class MyTargetProxy implements InvocationHandler {

    private Object target;
    private MyInterceptor interceptor;


    private MyTargetProxy(Object target, MyInterceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    //将拦截逻辑封装到拦截器中，有客户端生成目标类的代理类的时候一起传入，这样客户端就可以设置不同的拦截逻辑。
    public static Object bind(Object target, MyInterceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyTargetProxy(target, interceptor));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        interceptor.intercept();
        return method.invoke(target, args);
    }
}
