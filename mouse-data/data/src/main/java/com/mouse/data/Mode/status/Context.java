package com.mouse.data.Mode.status;

import lombok.Setter;

/**
 * Created by lwf on 2017/6/23.
 * use to do:
 */
public class Context {

    @Setter
    private Status status;

    private void changeStatus(Integer hours){

        if (hours>6 && hours < 18 ){
        }

    }
}
