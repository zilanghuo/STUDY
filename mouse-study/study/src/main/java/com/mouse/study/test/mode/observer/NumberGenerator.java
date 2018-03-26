package com.mouse.study.test.mode.observer;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lwf
 * @date 2018/3/26
 * use:
 */
@Data
public abstract class NumberGenerator {

    List<Observer> observerList = new ArrayList();

    public void addObserver(Observer observer){
        observerList.add(observer);
    }

    public void deleteObserver(Observer observer){
        observerList.remove(observer);
    }

    /**
     * 通知观察者：多个
     */
    public void notifyObserver(){
        Iterator<Observer> iterator = observerList.iterator();
        while (iterator.hasNext()){
            iterator.next().update(this);
        }
    }

    public abstract int getNumber();

    public abstract void execute();

}
