package com.mouse.study.controller;

import com.mouse.study.service.IHealthService;
import com.mouse.study.test.apollo.spring.ConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ConfigBean configBean;

    @RequestMapping(value = "/check")
    public String checkHealth() throws IOException {
        try {
            Map map = new HashMap();
            map.put("tableName", "dual");
            log.info("dual size:{}",healthService.checkHealth(map));
            map.put("tableName", "t_msg_message");
            healthService.checkHealth(map);
            log.info("t_msg_message size:{}",healthService.checkHealth(map));
            log.info("健康检查-----------------------------------");
            log.info("最大可用内存:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M\t");
            log.info("当前JVM空闲内存:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M\t");
            log.info("当前JVM占用的内存总数:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M\t");


            return "health";
        } catch (Exception e) {
            log.error("checkHealth error", e);
            return "FAIL";
        }
    }
}
