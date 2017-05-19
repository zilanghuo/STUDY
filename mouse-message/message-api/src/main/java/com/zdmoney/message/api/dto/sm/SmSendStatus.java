package com.zdmoney.message.api.dto.sm;

/**
 * 短信发送状态
 * Created by gaojc on 2016/11/3.
 */
public enum SmSendStatus {
    /**
     * 任务状态：9-待发送
     */
    WAITING(9, "待发送"),
    /**
     * 任务状态：0-任务中
     */
    SENDING(0, "发送中"),
    /**
     * 任务状态：1-成功
     */
    SUCCESS(1, "发送成功"),
    /**
     * 任务状态：2-失败
     */
    FAIL(2, "发送失败");

    private int value;
    private String msg;

    SmSendStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static SmSendStatus getShortMsgSendStatus(int value) {
        for (SmSendStatus status : SmSendStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}
