package com.mouse.data.controller;

import com.mouse.study.api.facade.IHessianFacadeService;
import com.mouse.vesta.client.VestaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lwf on 2017/6/6.
 */
@Slf4j
@Controller
@Scope("singleton")
@RequestMapping("/hessian")
public class HessianDemoController {

    @Autowired
    private IHessianFacadeService hessianFacadeService;

    @RequestMapping(value = "/test")
    public String test() {
        log.info("enter HessianDemoController test");
        hessianFacadeService.test();
        return "OK";
    }

    @RequestMapping(value = "/generator")
    public String generator() {
        String id = VestaClient.generatorId();
        log.info("vestaId:{}", id);
        return "OK";
    }

}
