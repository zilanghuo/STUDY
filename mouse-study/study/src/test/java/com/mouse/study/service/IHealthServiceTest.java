package com.mouse.study.service;

import com.mouse.study.api.facade.ITestFacadeService;
import com.mouse.study.facade.TestFacadeService;
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
        ITestFacadeService service = (TestFacadeService) ctx.getBean("testFacadeService");
        service.testMotan();
    }
}