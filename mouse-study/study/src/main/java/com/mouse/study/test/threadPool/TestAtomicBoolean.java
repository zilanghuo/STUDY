package com.mouse.study.test.threadPool;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lwf on 2017/8/1.
 * use to do:
 */
public class TestAtomicBoolean implements Runnable {
    public static volatile AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) {
        for (int j = 0; j < 20; j++) {
            Thread t = new Thread(new TestAtomicBoolean());
            t.start();
        }
    }

    public void run() {
        if (flag.compareAndSet(true, false)) {
            System.out.println("我成功了！");
        }
    }
}