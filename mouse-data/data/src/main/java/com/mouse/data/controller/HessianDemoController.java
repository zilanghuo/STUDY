package com.mouse.data.controller;

import com.mouse.study.api.facade.IHessianFacadeService;
import com.mouse.vesta.client.VestaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public String generator() throws Exception {
        final FileWriter fileWriter = new FileWriter("f:\\text.txt", true);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        testId(fileWriter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        fileWriter.flush();

        String id = VestaClient.generatorId();
        log.info("vestaId:{}", id);
        return "OK";
    }

    public static void testId(FileWriter fileWriter) {
        for (int i = 0; i < 10000; i++) {
            String id = VestaClient.generatorId();
            try {
                fileWriter.write(id + "\r");
            } catch (IOException e) {

            }
        }
    }

}
