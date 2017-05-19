package com.zdmoney.message.sm.vo;

/**
 * 第三方返回错误-创蓝通道
 * Created by gaojc on 2016/12/9.
 */
public enum BlueError {
    E_101("101", "无此用户"),
    E_102("102", "密码错"),
    E_103("103", "提交过快（提交速度超过流速限制）"),
    E_104("104", "系统忙（因平台侧原因，暂时无法处理提交的短信）"),
    E_105("105", "敏感短信（短信内容包含敏感词）"),
    E_106("106", "消息长度错（>536或<=0）"),
    E_107("107", "包含错误的手机号码"),
    E_108("108", "手机号码个数错（群发>50000或<=0;单发>200或<=0）"),
    E_109("109", "无发送额度（该用户可用短信数已使用完）"),
    E_110("110", "不在发送时间内"),
    E_111("111", "超出该账户当月发送额度限制"),
    E_112("112", "无此产品，用户没有订购该产品"),
    E_113("113", "extno格式错（非数字或者长度不对）"),
    E_115("115", "自动审核驳回"),
    E_116("116", "签名不合法，未带签名（用户必须带签名的前提下）"),
    E_117("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址"),
    E_118("118", "用户没有相应的发送权限"),
    E_119("119", "用户已过期"),
    E_120("120", "测试内容不是白名单");

    private String code;
    private String msg;

    BlueError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static BlueError getBlueErrorDesc(String code) {
        BlueError blueErrors[] = BlueError.values();
        for (BlueError blueError : blueErrors) {
            if (com.zdmoney.zdqd.util.StringUtils.equals(blueError.getCode(), code)) {
                return blueError;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BlueError {code='" + code + "', msg='" + msg + "'}";
    }
}
