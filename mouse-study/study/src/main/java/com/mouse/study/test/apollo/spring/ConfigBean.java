package com.mouse.study.test.apollo.spring;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.Data;
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
@Component("configBean")
@Data
public class ConfigBean {

    private static final Logger logger = LoggerFactory.getLogger(ConfigBean.class);

    @Value("${initialSize}")
    private String test;

    @PostConstruct
    void initialize() {
        logger.info("test is initialized as {}", test);
    }

}
