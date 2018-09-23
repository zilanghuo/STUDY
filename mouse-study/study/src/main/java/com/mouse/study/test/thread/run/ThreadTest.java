package com.mouse.study.test.thread.run;

/**
 * @author laiwufa
 * @date 2018/9/12
 * use:
 */
public class ThreadTest {

    public static void main(String[] args) throws Exception {
        Runner1 runner1 = new Runner1();
        Runner2 runner2 = new Runner2();
        Runner3 runner3 = new Runner3();
        //		Thread(Runnable target) 分配新的 Thread 对象。
        Thread thread1 = new Thread(runner1);
        Thread thread2 = new Thread(runner2);
        Thread thread3 = new Thread(runner3);
		thread1.start();
		thread2.start();
		thread3.start();
        //thread1.run();
        //thread2.run();
        //thread3.run();


        Thread.sleep(60 * 1000);
    }
}

class Runner1 implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner1运行状态——————————" + i);
        }
    }
}

class Runner2 implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner2运行状态==========" + i);
        }
    }
}

class Runner3 implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("进入Runner3运行状态==========" + i);
        }
    }
}