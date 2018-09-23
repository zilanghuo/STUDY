package com.mouse.study.test.proxy;

/**
 * @author laiwufa
 * @date 2018/9/7
 * use:
 */
public class TakeService implements ITakeService {
    @Override
    public void sayHello() {
        System.out.println("这是真正的实现方法");
    }

    @Override
    public void sayGoodBye() {
        System.out.println("这是真正的sayBye 方法");
    }
}
