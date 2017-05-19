package com.zdmoney.message.api.dto.push.enums;

/**
 * Created by gaojc on 2016/7/26.
 */
public enum DeviceType {
    ALL("所有类型", "0"),
    ANDROID("安卓", "1"),
    IOS("IOS", "2");

    private String msg;
    private String value;

    DeviceType(String msg, String value) {
        this.msg = msg;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static DeviceType getDeviceType(String value) {
        for (DeviceType deviceType : DeviceType.values()) {
            if (deviceType.getValue().equals(value)) {
                return deviceType;
            }
        }
        return null;
    }
}
