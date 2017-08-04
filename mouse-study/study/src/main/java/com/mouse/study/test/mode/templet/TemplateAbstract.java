package com.mouse.study.test.mode.templet;

/**
 * Created by lwf on 2017/8/4.
 * use to do:
 */
public abstract class TemplateAbstract {

    protected abstract void open();

    protected abstract void print();

    protected abstract void close();

    protected void book(){

    }

    public final void display(){
        open();
        book();
        for (int i = 0; i < 10; i++) {
            print();
        }
        close();
    }
}
