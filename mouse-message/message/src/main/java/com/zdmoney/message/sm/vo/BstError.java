package com.zdmoney.message.sm.vo;

import com.zdmoney.zdqd.util.StringUtils;

/**
 * 第三方返回错误-博士通通道
 * Created by gaojc on 2016/12/9.
 */
public enum BstError {
    E_2("-2", "提交参数错误"),
    E_1("-1", "鉴权失败，账号，口令或者IP不匹配"),
    E_4("4", "消息长度过长"),
    E_6("6", "超过最大信息长"),
    E_7("7", "业务代码错"),
    E_8("8", "提交速率过快"),
    E_9("9", "其他错误"),
    E_10("10", "未交押金"),
    E_11("11", "账户余额不足"),
    E_12("12", "签名信息错误"),
    E_13("13", "关键字(普通)"),
    E_14("14", "关键字(特殊)"),
    E_15("15", "号码超量"),
    E_16("16", "内容超量");

    private String code;
    private String msg;

    BstError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static BstError getBstErrorDesc(String code) {
        BstError bstErrors[] = BstError.values();
        for (BstError bstError : bstErrors) {
            if (StringUtils.equals(bstError.getCode(), code)) {
                return bstError;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BstError {code='" + code + "',msg='" + msg + "'}";
    }
}
