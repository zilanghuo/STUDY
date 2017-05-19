package com.zdmoney.message.api.dto.log;

/**
 * 统计频率
 * Created by user on 2017/2/23.
 */
public enum LogStatRate {
    DAY("每日", 1),
    HOUR("每小时", 2);

    private String msg;
    private Integer value;

    LogStatRate(String msg, Integer value) {
        this.msg = msg;
        this.value = value;
    }

    public static LogStatRate getStatRate(Integer code) {
        for (LogStatRate status : LogStatRate.values()) {
            if (status.getValue() == code) {
                return status;
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
