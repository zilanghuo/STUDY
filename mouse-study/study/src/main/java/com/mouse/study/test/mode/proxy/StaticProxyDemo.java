package com.mouse.study.test.mode.proxy;

/**
 * @author lwf
 * @date 2017/12/26
 * use: 静态代理
 */
public class StaticProxyDemo {
    public static void main(String args[]) {
        RealSubject subject = new RealSubject();
        StaticProxy p = new StaticProxy(subject);
        p.request();
    }
}

interface Subject {
    void request();
}

class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("request");
    }
}

class StaticProxy implements Subject {
    private Subject subject;

    public StaticProxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        System.out.println("PreProcess");
        subject.request();
        System.out.println("PostProcess");
    }
}