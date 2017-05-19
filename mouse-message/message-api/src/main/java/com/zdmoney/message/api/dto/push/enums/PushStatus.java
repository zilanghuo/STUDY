package com.zdmoney.message.api.dto.push.enums;

/**
 * Created by gaojc on 2016/7/26.
 */
public enum PushStatus {
    /**
     * 推送状态：9-待推送
     */
    WAITING("9", "待推送"),
    /**
     * 推送状态：0-推送中
     */
    PUSHING("0", "推送中"),
    /**
     * 推送状态：1-成功
     */
    SUCCEED("1", "推送成功"),
    /**
     * 推送状态：2-失败
     */
    FAILED("2", "推送失败"),
    /**
     * 推送状态：3-失败
     */
    STATED("3", "统计完成");

    private String value;
    private String msg;

    PushStatus(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static PushStatus getPushStatus(String value) {
        for (PushStatus pushStatus : PushStatus.values()) {
            if (pushStatus.getValue().equals(value)) {
                return pushStatus;
            }
        }
        return null;
    }
}
