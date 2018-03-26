package com.mouse.study.test.mode.observer;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
public class IntegerObserver implements Observer {
    @Override
    public void update(NumberGenerator generator) {
        System.out.println("*****************IntegerObserver:" + generator.getNumber());
    }
}
