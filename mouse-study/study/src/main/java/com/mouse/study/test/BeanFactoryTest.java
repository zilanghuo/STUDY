package com.mouse.study.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwf on 2017/7/19.
 * use to do:测试bean
 */
public class BeanFactoryTest {

    @org.junit.Test
    public void testSimpleLoad() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("test/beanFactoryTest.xml");
        MyTestBean testBean = (MyTestBean) beanFactory.getBean("myTestBean");
        String testStr = testBean.getTestStr();
        System.out.println(testStr);
    }
}
