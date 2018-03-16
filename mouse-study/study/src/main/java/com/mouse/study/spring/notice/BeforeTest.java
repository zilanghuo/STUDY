package com.mouse.study.spring.notice;

import java.lang.reflect.Method;

/**
 * @author lwf
 * @date 2018/3/16
 * use:
 */
public class BeforeTest implements org.springframework.aop.MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        System.out.println("------------------------before 通知");
    }
}
