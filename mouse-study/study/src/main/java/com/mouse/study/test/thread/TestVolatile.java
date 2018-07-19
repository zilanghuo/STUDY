package com.mouse.study.test.thread;

/**
 * Created by lwf on 2017/8/1.
 * use to do:
 */
public class TestVolatile implements Runnable {
    public static volatile boolean flag = true;

    public static void main(String[] args) {
        for (int j = 0; j < 20; j++) {
            Thread t = new Thread(new TestVolatile());
            t.start();
        }
    }

    @Override
    public void run() {
        if (flag) {
            System.out.println("我成功了！");
            flag = false;
        }
    }

}
