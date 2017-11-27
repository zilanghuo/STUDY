package com.mouse.study.test.log;

import org.apache.log4j.MDC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lwf
 * @date 2017/11/27
 * use:
 */
public class MdcRunnable implements Runnable {

    public static final org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getRootLogger();

    private static AtomicInteger size = new AtomicInteger(0);

    @Override
    public void run() {
        size.incrementAndGet();
        org.apache.log4j.MDC.put("traceId",size);
        logger.info("---------------------");
        System.out.println("runnable:"+ MDC.get("traceId"));
    }
}
