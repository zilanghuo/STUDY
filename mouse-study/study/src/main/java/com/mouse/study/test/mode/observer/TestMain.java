package com.mouse.study.test.mode.observer;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
public class TestMain {

    public static void main(String[] args) {
        NumberGenerator generator = new IncrementNumberGenerator(10,50,5);
        Observer observer = new DigitObserver();
        Observer observer1 = new GraphObserver();
        Observer observer2 = new IntegerObserver();
        generator.addObserver(observer);
        generator.addObserver(observer1);
        generator.addObserver(observer2);
        generator.execute();

    }

    private static void test1() {
        NumberGenerator generator = new RandomNumberGenerator();
        Observer observer = new DigitObserver();
        Observer observer1 = new GraphObserver();
        generator.addObserver(observer);
        generator.addObserver(observer1);
        generator.execute();
    }

}
