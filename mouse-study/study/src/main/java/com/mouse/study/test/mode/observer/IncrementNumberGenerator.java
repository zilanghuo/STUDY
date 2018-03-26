package com.mouse.study.test.mode.observer;

import lombok.NoArgsConstructor;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
public class IncrementNumberGenerator extends NumberGenerator {

    private int step;

    private int begin;

    private int end;

    private int number;

    public IncrementNumberGenerator(int begin, int end, int step) {
        this.begin = begin;
        this.end = end;
        this.step = step;

    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i = begin; i < end; i = i + step) {
            number = i;
            notifyObserver();
        }
    }
}
