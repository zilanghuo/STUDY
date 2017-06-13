package com.mouse.vesta.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class VestaServer {

    @org.junit.Test
    public static   void initServer() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "vestaServer/vesta-server-main.xml");
        context.start();

    }

}
