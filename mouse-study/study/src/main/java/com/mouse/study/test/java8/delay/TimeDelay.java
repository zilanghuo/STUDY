package com.mouse.study.test.java8.delay;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lwf on 2017/9/11.
 * use to do:
 */
public class TimeDelay {


    @org.junit.Test
    public void testTimer() throws Exception {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Timer timer = new Timer();//实例化Timer类
            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println("退出"+ finalI);
                    this.cancel();
                }
            }, 200);//五百毫秒
        }
        Thread.sleep(5000);
        System.out.println("---");
    }

}
