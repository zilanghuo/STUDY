package com.mouse.study.test.thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author lwf
 * @date 2017/8/1
 */
public class TestAtomicBoolean implements Runnable {

    public static volatile AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) {
        for (int j = 0; j < 20; j++) {
            Thread t = new Thread(new TestAtomicBoolean());
            t.start();
        }
    }

    @Override
    public void run() {
        if (flag.compareAndSet(true, false)) {
            System.out.println("我成功了！");
        }
    }
}