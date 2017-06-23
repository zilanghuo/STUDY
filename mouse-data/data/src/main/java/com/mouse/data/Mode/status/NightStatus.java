package com.mouse.data.Mode.status;

/**
 * Created by lwf on 2017/6/23.
 * use to do:
 */
public class NightStatus implements Status {
    @Override
    public void openDoor() {
        System.out.println("晚上开门，用钥匙");
    }

    @Override
    public void closeDoor() {
        System.out.println("晚上关门，锁门");
    }
}
