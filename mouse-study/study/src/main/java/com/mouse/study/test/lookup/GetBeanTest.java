package com.mouse.study.test.lookup;

/**
 * Created by lwf on 2017/7/20.
 * use to do:
 */
public abstract class GetBeanTest {
    public void showMe() {
        this.getBean().showMe();
    }

    public abstract User getBean();
}
