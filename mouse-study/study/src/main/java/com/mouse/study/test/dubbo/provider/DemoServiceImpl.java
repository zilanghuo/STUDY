package com.mouse.study.test.dubbo.provider;

/**
 * @author lwf
 * @date 2017/10/30
 * use:
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}