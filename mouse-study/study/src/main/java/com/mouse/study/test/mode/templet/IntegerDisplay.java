package com.mouse.study.test.mode.templet;

/**
 * Created by lwf on 2017/8/4.
 * use to do:
 */
public class IntegerDisplay extends TemplateAbstract {
    @Override
    public void open() {
        System.out.println("-----------open Integer");
    }

    @Override
    public void print() {
        System.out.println("print Integer");
    }

    @Override
    public void close() {
        System.out.println("----------close Integer");
    }
}
