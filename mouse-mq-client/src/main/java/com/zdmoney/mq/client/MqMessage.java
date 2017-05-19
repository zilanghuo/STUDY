package com.zdmoney.mq.client;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rui on 16/8/25.
 */
@Data
public class MqMessage implements Serializable {

    private String key;

    private String data;

    public MqMessage() {
    }

    public MqMessage(String key, String data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "key='" + key + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
