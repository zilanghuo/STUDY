package com.zdmoney.message.push.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gaojc on 2016/8/5.
 */
@Data
@NoArgsConstructor
public class PushResult {
    //返回码
    private String retCode;
    //状态
    private String status;
    //返回任务号
    private String retTaskNo;
    //消息有效可下发总数
    private Integer sendNum;
    //消息回执总数
    private Integer arriverNum;
    //用户点击数
    private Integer clickNum;
    //详细
    private String details;

    public PushResult(String msgTotal, String msgProcess, String clickNum) {
        this.sendNum = Integer.parseInt(msgTotal);
        this.arriverNum = Integer.parseInt(msgProcess);
        this.clickNum = Integer.parseInt(clickNum);
    }

    public PushResult(String retCode) {
        this.retCode = retCode;
    }

    private static final String retCodes = "ok,successed_online,successed_offline";

    public static String getRetCodes() {
        return retCodes;
    }

}
