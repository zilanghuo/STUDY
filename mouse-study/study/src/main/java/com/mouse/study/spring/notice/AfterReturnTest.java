package com.mouse.study.spring.notice;

import java.lang.reflect.Method;

/**
 * @author lwf
 * @date 2018/3/16
 * use:
 */
public class AfterReturnTest implements org.springframework.aop.AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("-------------------------------after 通知");
    }
}
