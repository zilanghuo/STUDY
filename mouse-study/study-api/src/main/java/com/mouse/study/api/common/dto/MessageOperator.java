package com.mouse.study.api.common.dto;

/**
 * Created by rui on 15/8/26.
 */
public enum MessageOperator {

    SYS("系统");

    private String message;

    MessageOperator(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
