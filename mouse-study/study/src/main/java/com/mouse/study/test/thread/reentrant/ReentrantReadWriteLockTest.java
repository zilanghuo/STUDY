package com.mouse.study.test.thread.reentrant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lwf
 * @date 2018/5/28
 * use:
 */
public class ReentrantReadWriteLockTest {


    final static ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());

    final static ExecutorService writeService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());

    public static void main(String[] args) throws Exception {
        final LockQueue queue = new LockQueue();

        for (int i = 0; i < 50; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    queue.get();
                }
            });
        }

        for (int i = 0; i < 50; i++) {
            writeService.execute(new Runnable() {
                @Override
                public void run() {
                    queue.put("hello world");
                    System.out.println("--" + queue.rwl.hasQueuedThreads());
                    System.out.println("length:" + queue.rwl.getQueueLength());
                }
            });
        }
    }
}
