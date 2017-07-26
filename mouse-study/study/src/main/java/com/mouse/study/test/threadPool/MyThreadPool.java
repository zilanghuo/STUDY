package com.mouse.study.test.threadPool;

import com.mouse.study.utils.JackJsonUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by lwf on 2017/7/25.
 * use to do:
 */
public class MyThreadPool {

    @org.junit.Test
    public void getCPU() throws Exception {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @org.junit.Test
    public void init() throws Exception {

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            Future future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行速度：" + finalI);
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }*/
                }
            });
            System.out.println(JackJsonUtil.objToStr(future.get(1, TimeUnit.SECONDS)));
        }
        executorService.shutdown();
        Thread.sleep(10000);
    }

}
