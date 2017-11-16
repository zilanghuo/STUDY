package com.mouse.study.test.mode.watch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf
 * @date 2017/11/16
 * use:
 */
public abstract class Subject {

    private List<Observer> list = new ArrayList<Observer>();

    public void attach(Observer observer) {
        list.add(observer);
        System.out.println("Attached an observer");
    }

    public void detach(Observer observer) {
        list.remove(observer);
    }

    public void notifyObservers(String newState) {
        for (Observer observer : list) {
            observer.update(newState);
        }
    }

}
