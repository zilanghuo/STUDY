package com.zdmoney.message.sm.vo;

import com.zdmoney.zdqd.util.StringUtils;

/**
 * Created by gaojc on 2017/1/6.
 */
public enum Dh3tError {
    E_1("1", "账号无效"),
    E_2("2", "密码错误"),
    E_3("3", "msgid太长，不得超过32位"),
    E_5("5", "手机号码个数超过最大限制(500个)"),
    E_6("6", "短信内容超过最大限制(350字)"),
    E_7("7", "扩展子号码无效"),
    E_8("8", "定时时间格式错误"),
    E_14("14", "手机号码为空"),
    E_19("19", "用户被禁发或禁用"),
    E_20("20", "ip鉴权失败"),
    E_21("21", "短信内容为空"),
    E_22("22", "数据包大小不匹配"),
    E_98("98", "系统正忙"),
    E_99("99", "消息格式错误");

    private String code;
    private String msg;

    Dh3tError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static Dh3tError getDh3tErrorDesc(String code) {
        Dh3tError dh3tErrors[] = Dh3tError.values();
        for (Dh3tError dh3tError : dh3tErrors) {
            if (StringUtils.equals(dh3tError.getCode(), code)) {
                return dh3tError;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Dh3tError {code='" + code + "', msg='" + msg + "'}";
    }
}
