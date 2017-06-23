package com.mouse.study.controller;

import com.mouse.study.service.IHealthService;
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

    @RequestMapping(value = "/check")
    public String checkHealth() throws IOException {
        try {
            Map map = new HashMap();
            map.put("tableName", "dual");
            healthService.checkHealth(map);
            map.put("tableName", "t_msg_message");
            healthService.checkHealth(map);
            log.info("健康检查-----------------------------------");
            log.info("最大可用内存:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M\t");
            log.info("当前JVM空闲内存:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M\t");
            log.info("当前JVM占用的内存总数:" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M\t");
            log.info("-----------------------------------");
            return "health";
        } catch (Exception e) {
            log.error("checkHealth error", e);
            return "FAIL";
        }
    }


    @RequestMapping(value = "/vesteId")
    public String vesteId() throws IOException {


        return null;
    }
}
