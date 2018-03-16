package com.mouse.study.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lwf
 * @date 2018/3/15
 * use:
 */
public class TestMain {

    public static void main(String[] args) throws Exception {

        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("springTest/eventTest.xml");
        //创建一个事件对象
        EmailEvent emailEvent = new EmailEvent("hello Spring!", "cxg@126.com", "This is SpringApplicatoinContext test!");
        //主动触发事件监视机制
        context.publishEvent(emailEvent);



    }


}
