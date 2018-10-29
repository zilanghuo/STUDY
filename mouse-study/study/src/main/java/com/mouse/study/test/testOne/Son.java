package com.mouse.study.test.testOne;

/**
 * @author laiwufa
 * @date 2018/10/26
 * use:
 */
public class Son  extends Parent{
    @Override
    protected void doSomething(){
        System.out.println("son do");
        super.doSomething();
    }

    public static void main(String[] args) {
        Parent parent = new Son();
        parent.doSomething();
    }

}

class Parent{

    protected void doSomething(){
        System.out.println("father do");
        this.doSomething();
    }
}