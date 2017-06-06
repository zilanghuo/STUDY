package com.mouse.data.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lwf on 2017/6/6.
 */
@Slf4j
@Controller
@Scope("singleton")
@RequestMapping("/health")
public class HealthCheckController {

    @RequestMapping(value = "/check")
    public String checkHealth() {
        log.info("enter checkHealth");
       return "OK";
    }
}
