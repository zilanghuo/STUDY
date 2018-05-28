package com.mouse.study.test.threadPool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lwf
 * @date 2018/5/28
 * use:
 */
public class ThreadFactoryTest implements ThreadFactory {

    AtomicInteger size = new AtomicInteger(0);

    private ThreadFactoryTest(){}

    public static ThreadFactoryTest getDefault(){
       return new ThreadFactoryTest();
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("自定义-" + size.incrementAndGet());
        return thread;
    }
}
