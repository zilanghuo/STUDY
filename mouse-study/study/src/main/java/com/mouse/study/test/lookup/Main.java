package com.mouse.study.test.lookup;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwf on 2017/7/20.
 * use to do:
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("test/lookupTest.xml");
        GetBeanTest testBean = (GetBeanTest) beanFactory.getBean("getBeanTest");
        testBean.showMe();

    }
}
