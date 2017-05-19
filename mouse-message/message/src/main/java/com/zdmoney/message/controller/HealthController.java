package com.zdmoney.message.controller;

import com.zdmoney.message.message.service.IHealthCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lwf on 2017/5/17.
 */
@Controller
@Scope("singleton")
@RequestMapping("/health")
public class HealthController {

    private final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private IHealthCheckService checkHealth;

    @RequestMapping(value = "/check")
    @ResponseBody
    public String checkHealth() {
        try {
            logger.info("--------------------健康检查开始---------------------------");
            logger.info("checkHealth:i = {}", checkHealth.checkHealth());
            logger.info("最大可用内存:" + Runtime.getRuntime().maxMemory() / 1024  / 1024 + "M\t");
            logger.info("当前JVM空闲内存:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M\t");
            logger.info("当前JVM占用的内存总数:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M\t");
            logger.info("-----------------------------------");
            return "OK";
        } catch (Exception e) {
            logger.error("checkHealth error", e);
            return "FAIL";
        }
    }
}
