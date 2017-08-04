package com.mouse.study.test.mode.templet;

/**
 * Created by lwf on 2017/8/4.
 * use to do:
 */
public class StringDisplay extends TemplateAbstract {
    @Override
    public void open() {
        System.out.println("-----------open String");
    }

    @Override
    public void print() {
        System.out.println("print string");
    }

    @Override
    public void close() {
        System.out.println("----------close String");
    }
}
