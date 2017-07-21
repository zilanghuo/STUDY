package com.mouse.study.test;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by lwf on 2017/7/19.
 * use to do:测试bean
 */
@Slf4j
public class BeanFactoryTest {



    @org.junit.Test
    public void testSimpleLoad2(){
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("test/beanFactoryTest.xml"));
        MyTestBean testBean = (MyTestBean) beanFactory.getBean("myTestBean");
        String testStr = testBean.getTestStr();
        log.info(testStr);
    }

    @org.junit.Test
    public void testSimpleLoad() {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("test/beanFactoryTest.xml");
        MyTestBean testBean = (MyTestBean) beanFactory.getBean("myTestBean");
        String testStr = testBean.getTestStr();
        Assert.assertEquals("testStr",testStr);
    }

}
