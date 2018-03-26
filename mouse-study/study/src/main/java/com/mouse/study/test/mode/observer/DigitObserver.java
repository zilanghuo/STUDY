package com.mouse.study.test.mode.observer;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
public class DigitObserver implements Observer {
    @Override
    public void update(NumberGenerator generator) {
        System.out.println("----------------------------digitObserver:" + generator.getNumber());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
    }
}
