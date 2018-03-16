package com.mouse.study.spring.notice;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lwf
 * @date 2018/3/16
 * use:
 */
public class AroundTest implements org.aopalliance.intercept.MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("-------------------around test 环绕通知");
        return null;
    }
}
