package com.mouse.study.test.replacedmethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwf on 2017/7/20.
 * use to do:
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("test/replaceMethodTest.xml");
        TestChangeMethod testBean = (TestChangeMethod) beanFactory.getBean("testChangeMethod");

        testBean.changeMe();
    }
}
