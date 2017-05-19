package com.zdmoney.mq.client;

import lombok.Data;

import java.util.Arrays;

/**
 * Created by rui on 16/8/25.
 */
@Data
public class MqByteMessage {

    private String key;

    private byte[] data;

    public MqByteMessage() {
    }

    public MqByteMessage(String key, byte[] data) {
        this.key = key;
        this.data = data;
    }


    @Override
    public String toString() {
        return "MqByteMessage{" +
                "key='" + key + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
