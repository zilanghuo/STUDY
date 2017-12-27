package com.mouse.study.test.mode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lwf
 * @date 2017/12/26
 * use:
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        DynamicRealSubject realSubject = new DynamicRealSubject();    //1.创建委托对象
        ProxyHandler handler = new ProxyHandler(realSubject);   //2.创建调用处理器对象
        DynamicSubject proxySubject = (DynamicSubject) Proxy.newProxyInstance(DynamicRealSubject.class.getClassLoader(),
                DynamicRealSubject.class.getInterfaces(), handler);    //3.动态生成代理对象
        proxySubject.request(); //4.通过代理对象调用方法
    }
}

/**
 * 接口
 */
interface DynamicSubject {
    void request();
}

/**
 * 委托类
 */
class DynamicRealSubject implements DynamicSubject {
    @Override
    public void request() {
        System.out.println("====RealSubject Request====");
    }
}

/**
 * 代理类的调用处理器
 */
class ProxyHandler implements InvocationHandler {
    private DynamicSubject subject;

    public ProxyHandler(DynamicSubject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("====before====");//定义预处理的工作，当然你也可以根据 method 的不同进行不同的预处理工作
        Object result = method.invoke(subject, args);
        System.out.println("====after====");
        return result;
    }
}

