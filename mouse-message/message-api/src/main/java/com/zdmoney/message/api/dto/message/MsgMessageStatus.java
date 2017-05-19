package com.zdmoney.message.api.dto.message;

/**
 * Created by lwf on 2016/8/15.
 */
public enum MsgMessageStatus {
    UN_READ("未读",1),
    ALREADY_READ("已读",2);
    private String msg;
    private Integer value;

    MsgMessageStatus(String msg, Integer value){
        this.msg = msg;
        this.value = value;
    }

    public static MsgMessageStatus getMessageStatus(String code){
        if(MsgMessageStatus.UN_READ.getValue().equals(code)){
            return MsgMessageStatus.UN_READ;
        }else if(MsgMessageStatus.ALREADY_READ.equals(code)){
            return MsgMessageStatus.ALREADY_READ;
        }
        return MsgMessageStatus.UN_READ;
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
