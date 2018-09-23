package com.mouse.study.test.proxy.staticProxy;

import com.mouse.study.test.proxy.ITakeService;

/**
 * @author laiwufa
 * @date 2018/9/23
 * use:
 */
public class TakeProxyService implements ITakeService {
    private ITakeService takeService;
    TakeProxyService(ITakeService takeService){
        this.takeService = takeService;
    }
    @Override
    public void sayHello() {
        System.out.println("这是代理方法");
        takeService.sayHello();
    }

    @Override
    public void sayGoodBye() {

    }
}
