package com.zdmoney.message.api.dto.message;

/**
 * Created by lwf on 2016/8/15.
 */
public enum MsgMessageType {
    ACTIVITY("活动",1),
    NOTICE("公告",2);


    private String msg;
    private Integer value;

    MsgMessageType(String msg,Integer value){
        this.msg = msg;
        this.value = value;
    }
    public static MsgMessageType getMessageType(Integer code){
        if(MsgMessageType.ACTIVITY.getValue().equals(code)){
            return MsgMessageType.ACTIVITY;
        }else if(MsgMessageType.NOTICE.getValue().equals(code)){
            return MsgMessageType.NOTICE;
        }
        return MsgMessageType.ACTIVITY;
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
