package com.zdmoney.message.api.dto.sm;

/**
 * 短信通道状态
 * Created by gaojc on 2016/11/7.
 */
public enum SmChannelStatus {
    OFF(0, "停用"),        //停用
    ON(1, "启用");       //启用

    private int value;
    private String msg;

    SmChannelStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static SmChannelStatus getChannelStatus(int value) {
        for (SmChannelStatus status : SmChannelStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}
