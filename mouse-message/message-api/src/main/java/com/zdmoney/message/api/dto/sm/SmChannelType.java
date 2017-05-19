package com.zdmoney.message.api.dto.sm;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/11/3.
 * 账户类型
 */
@NoArgsConstructor
public enum SmChannelType {
    BST(0, "BST", "博士通", "uid", "pwd"),
    BLUE(5, "253", "创蓝", "account", "pswd"),
    DH3T(6, "DH3T", "大汉三通", "account", "pswd");

    private int value;
    private String no;
    private String name;
    private String userPrifix;//用户名对应的名称
    private String pwdPrifix;//密码对应的名称

    SmChannelType(int value, String no) {
        this.value = value;
        this.no = no;
    }

    SmChannelType(int value, String no, String name, String userPrifix, String pwdPrifix) {
        this.value = value;
        this.no = no;
        this.name = name;
        this.userPrifix = userPrifix;
        this.pwdPrifix = pwdPrifix;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getUserPrifix() {
        return userPrifix;
    }

    public String getPwdPrifix() {
        return pwdPrifix;
    }

    public int getValue() {
        return value;
    }

    public static SmChannelType getSmChannelType(String no) {
        for (SmChannelType smChannelType : SmChannelType.values()) {
            if (smChannelType.getNo().equals(no)) {
                return smChannelType;
            }
        }
        return null;
    }

    public static SmChannelType getSmChannelTypeByValue(String value) {
        for (SmChannelType smChannelType : SmChannelType.values()) {
            if (StringUtils.equals((smChannelType.getValue()+""), value)) {
                return smChannelType;
            }
        }
        return null;
    }

}
