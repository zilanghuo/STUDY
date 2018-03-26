package com.mouse.study.test.mode.observer;

import java.util.Random;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
public class RandomNumberGenerator extends NumberGenerator {

    private Random random = new Random();

    private int number;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i = 0; i < 20; i++) {
            number = random.nextInt(20);
            notifyObserver();
        }
    }
}