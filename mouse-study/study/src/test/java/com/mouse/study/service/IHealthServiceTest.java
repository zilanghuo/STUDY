package com.mouse.study.service;

import com.mouse.study.service.impl.HealthServiceImpl;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwf on 2017/6/5.
 */
public class IHealthServiceTest extends TestCase {

    @org.junit.Test
    public void testTestMotan() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:appContext_motan.xml");
        IHealthService service = (HealthServiceImpl) ctx.getBean("healthService");
        service.testMotan();
    }
}