package com.zdmoney.message.push.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/8/4.
 */
@Component
@Data
public class GetuiConfig {
    @Value("${msg.push.appId}")
    private String appId;
    @Value("${msg.push.appKey}")
    private String appKey;
    @Value("${msg.push.host}")
    private String host;
    @Value("${msg.push.masterSecret}")
    private String masterSecret;
    @Value("${msg.push.pushNeedDetails}")
    private String pushNeedDetails;
    @Value("${msg.push.offLineTime}")
    private long offLineTime;

}
