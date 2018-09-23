package com.mouse.study.test.proxy.reflectProxy;

/**
 * @author laiwufa
 * @date 2018/9/23
 * use:
 */
public class PutService implements IPutService {
    @Override
    public void get() {
        System.out.println("real,get");
    }

    @Override
    public void put() {
        System.out.println("real,put");
    }
}
