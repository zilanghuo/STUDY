package com.mouse.study.test.mode.watch;

/**
 * @author lwf
 * @date 2017/11/16
 * use: 具体观察者
 */
public class ConcreteObserverTwo implements Observer {

    private String observerState;

    @Override
    public void update(String state) {
        observerState = state;
        System.out.println("ConcreteObserverTwo："+observerState);
    }
}
