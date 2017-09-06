package com.mouse.study;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.runners.model.InitializationError;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lwf on 2017/8/23.
 * use to do:
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {
    static {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        String userDir = System.getProperty("user.dir");
        String fileUrl = userDir + "//src//test//config//logback.xml";
        System.out.println("userDir:{}" + userDir);
        System.setProperty("global.config.path", userDir + "/src/test/config/");
        System.setProperty("logger.logback.logpath", "E:/conf/0906/study/logs/");
        try {
            configurator.doConfigure(fileUrl);//加载logback配置文件
        } catch (JoranException e) {
            e.printStackTrace();
        }
    }

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
