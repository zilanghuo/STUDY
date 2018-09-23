package com.mouse.study.test.proxy.staticProxy;

import com.mouse.study.test.proxy.ITakeService;
import com.mouse.study.test.proxy.TakeService;

/**
 * @author laiwufa
 * @date 2018/9/23
 * use:
 */
public class TestMain {
    public static void main(String[] args) {
        ITakeService realTakeService = new TakeService();
        ITakeService proxyServece = new TakeProxyService(realTakeService);
        proxyServece.sayHello();
    }
}
