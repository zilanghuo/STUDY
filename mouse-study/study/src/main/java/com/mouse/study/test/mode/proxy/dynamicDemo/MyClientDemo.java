package com.mouse.study.test.mode.proxy.dynamicDemo;

/**
 * @author lwf
 * @date 2018/1/2
 * use:
 */
public class MyClientDemo {

    public static void main(String[] args) {
        MyInterceptor interceptor = new MyInterceptor() {
            @Override
            public void intercept() {
                System.out.println("hello world");
            }
        };
        MyTarget target = new MyTargetImpl();
        target.execute();
        System.out.println("----------------代理前----------------------");
        target = (MyTarget) MyTargetProxy.bind(target,interceptor);
        target.execute();
    }
}
