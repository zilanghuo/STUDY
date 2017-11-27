package com.mouse.study.test.log;

import org.apache.log4j.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lwf
 * @date 2017/11/27
 * use: 测试MDC在现场中的作用
 */
public class ThreadLocalMDC {

    public static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getRootLogger();

    public static void main(String[] args) {
        int nThreads = 3;
        ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue());
        org.apache.log4j.MDC.put("traceId", "2321");
        logger.info("hello");
        for (int i = 0; i < 5; i++) {
            executorService.execute(new MdcRunnable());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main:" + MDC.get("traceId"));

    }

}
