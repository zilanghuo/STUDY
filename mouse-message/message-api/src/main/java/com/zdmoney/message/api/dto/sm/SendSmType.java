package com.zdmoney.message.api.dto.sm;

/**
 * Created by Administrator on 2016/11/3.
 */
public enum SendSmType {
    MARKET(5, "营销短信"),
    VERIFY(10, "验证码短信"),
    NOTIFY(15, "通知短信");

    private Integer value;
    private String name;

    SendSmType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
