package com.mouse.study.test.threadPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author lwf
 * @date 2017/7/25
 * use to do:
 */
public class MyThreadPool {

    public static void main(String[] args) {
        AtomicInteger size = new AtomicInteger(1);
        System.out.println(size.getAndIncrement());
        System.out.println(size.getAndIncrement());
    }

    @org.junit.Test
    public void getCPU() throws Exception {
        final ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "---------"));
        }
        Thread.sleep(5000);
    }

    @org.junit.Test
    public void init() throws Exception {
        final ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), ThreadFactoryTest.getDefault());
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "---------");
                }
            });
        }
        executorService.shutdown();
        Thread.sleep(5000);
    }

}
