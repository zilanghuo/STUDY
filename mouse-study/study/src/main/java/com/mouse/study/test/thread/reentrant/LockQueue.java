package com.mouse.study.test.thread.reentrant;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lwf
 * @date 2018/5/28
 * use:
 */
public class LockQueue {

    public  ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private Object data = null;

    public void get() {
        rwl.readLock().lock();
        System.out.println("get:" + Thread.currentThread().getName() + "before");
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        System.out.println("get:" + Thread.currentThread().getName() + "after"+data);
        rwl.readLock().unlock();
    }

    public void put(String data) {
        rwl.writeLock().lock();
        System.out.println("write:" + Thread.currentThread().getName() + "before ");
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        this.data = data;
        System.out.println("write:" + Thread.currentThread().getName() + "after " + this.data);
        rwl.writeLock().unlock();
    }


}
