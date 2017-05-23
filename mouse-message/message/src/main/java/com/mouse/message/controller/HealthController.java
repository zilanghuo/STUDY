package com.mouse.message.controller;

import com.mouse.message.service.IHealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by lwf on 2017/5/23.
 */
@Slf4j
@Controller
@Scope("singleton")
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private IHealthService healthService;

    @RequestMapping(value = "/check")
    @ResponseBody
    public String checkHealth() throws IOException {
        try {
            healthService.checkHealth();
            log.info("健康检查-----------------------------------");
            log.info("最大可用内存:" + Runtime.getRuntime().maxMemory() / 1024  / 1024 + "M\t");
            log.info("当前JVM空闲内存:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M\t");
            log.info("当前JVM占用的内存总数:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M\t");
            log.info("-----------------------------------");
            return "OK";
        } catch (Exception e) {
            log.error("checkHealth error", e);
            return "FAIL";
        }
    }
}
