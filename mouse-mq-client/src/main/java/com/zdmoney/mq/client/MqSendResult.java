package com.zdmoney.mq.client;

import lombok.Data;

/**
 * Created by rui on 16/8/25.
 */
@Data
public class MqSendResult {

    private Status status;


    private String message;

    public MqSendResult(Status status) {
        this.status = status;
    }

    public MqSendResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        return (!isFail());
    }


    public boolean isFail() {
        return Status.FAIL == status;
    }

    public static enum Status {
        SUCCESS, FAIL, PROCESSING
    }

    @Override
    public String toString() {
        return "MqSendResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}


