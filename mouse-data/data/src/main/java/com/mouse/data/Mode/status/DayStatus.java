package com.mouse.data.Mode.status;

/**
 * Created by lwf on 2017/6/23.
 * use to do:
 */
public class DayStatus implements Status {
    @Override
    public void openDoor() {
        System.out.println("白天开门，不用钥匙");
    }

    @Override
    public void closeDoor() {
        System.out.println("白天关门，不锁");
    }
}
