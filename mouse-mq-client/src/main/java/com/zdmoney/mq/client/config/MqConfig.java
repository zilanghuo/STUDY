package com.zdmoney.mq.client.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rui on 16/8/24.
 */
@Data
@NoArgsConstructor
public class MqConfig {

    private String address;

    public MqConfig(String mqAddress) {
        this.address = mqAddress;
    }
}
