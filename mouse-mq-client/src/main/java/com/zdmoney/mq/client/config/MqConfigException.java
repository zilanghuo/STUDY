package com.zdmoney.mq.client.config;

/**
 * Created by rui on 16/8/25.
 */
public class MqConfigException extends RuntimeException {
    public MqConfigException() {
        super();
    }

    public MqConfigException(String message) {
        super(message);
    }

    public MqConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public MqConfigException(Throwable cause) {
        super(cause);
    }

    protected MqConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
