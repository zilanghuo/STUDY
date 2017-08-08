package com.mouse.study.test.apollo.spring;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by lwf on 2017/8/8.
 * use to do:更新不能重新载入
 */
@Configuration
@EnableApolloConfig(value = "application",order = 11)
@Component
public class ConfigBean {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBean.class);


    @Value("${test:200}")
    private String test;

    @Getter
    private String testTwo;

    @Value("${test_two:100}")
    public void setTestTwo(String testTwo) {
        this.testTwo = testTwo;
    }

    @PostConstruct
    void initialize() {
        logger.info("test is initialized as {}", test);
        logger.info("testTwo is initialized as {}", testTwo);
    }

}
