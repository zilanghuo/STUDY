package com.mouse.study.test.mode.proxy.dynamicDemo;

/**
 * @author lwf
 * @date 2018/1/2
 * use:
 */
public class MyTargetImpl implements MyTarget {
    @Override
    public void execute() {
        System.out.println("execute......................");
    }
}
