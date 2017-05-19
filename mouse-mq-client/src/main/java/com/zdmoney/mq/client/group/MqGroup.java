package com.zdmoney.mq.client.group;

import com.zdmoney.mq.client.config.MqConfigException;

/**
 * Created by rui on 16/9/7.
 */
public enum MqGroup {

    TEST("测试专用", "TEST", "TEST"),
    //消息系统
    MESSAGE_PUSH("消息系统－推送", "MESSAGE_PUSH", "push"),
    MESSAGE_MESSAGE("消息系统－消息", "MESSAGE_MESSAGE", "message"),
    MESSAGE_MESSAGET_INSERT_NOTIFY("消息系统-消息-增加回调","MESSAGE_MESSAGE","message_insert_notify"),
    MESSAGE_MESSAGET_READ_NOTIFY("消息系统-消息-读取消息回调","MESSAGE_MESSAGE","message_read_notify"),
    MESSAGE_NOTIFY_SM("消息系统－通知短信", "MESSAGE_NOTIFY_SM", "notify_sm"),
    MESSAGE_VERIFY_SM("消息系统-验证码短信", "MESSAGE_VERIFY_SM", "verify_sm"),
    MESSAGE_MARKET_SM("消息系统-营销短信", "MESSAGE_MARKET_SM", "market_sm"),
    MESSAGE_UPDATE_SM("消息系统-更新短信", "MESSAGE_UPDATE_SM", "update_sm"),

    LOG_DATA("消息系统-日志记录", "LOG_DATA", "log_data"),
    //标的系统
    ASSETS_SIGN("标的系统-签名", "ASSETS", "sign"),
    ASSETS_NOT_CREDIT("标的系统-通知信贷", "ASSETS", "notiCredit"),
    ASSETS_NOT_PARTNER("标的系统-通知合作方", "ASSETS", "notiPartner"),
    ASSETS_NOT_ASSETS("标的系统-通知内部系统", "ASSETS", "notiAssets"),
    //捞财宝
    LCB_GROUP("捞财宝系统", "LCB_GROUP", "push"),
    LCB_MANAGER("捞财宝后台管理系统", "LCB_MANAGER", "recharge"),
    //账户系统
    INTEGRAL_WITHDRAW("账户系统-提现","INTEGRAL_WITHDRAW","withdraw"),
    INTEGRAL_WITHDRAW_NOTIFY("账户系统-提现-通知","INTEGRAL_WITHDRAW_NOTIFY","withdraw_notify"),
    INTEGRAL_RECHARGE_NOTIFY("账户系统-充值-通知","INTEGRAL_RECHARGE_NOTIFY","recharge_notify"),
    INTEGRAL_TENDER_NOTIFY("账户系统-投资归集-通知","INTEGRAL_TENDER_NOTIFY","tender_notify");

    private String message;

    private String topic;

    private String tag;


    MqGroup(String message, String topic, String tag) {
        this.message = message;
        this.topic = topic;
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }


    public static void validate(String group, String topic, String tag) {
        boolean isExit = false;
        MqGroup exitGroup = null;
        MqGroup[] values = MqGroup.values();
        for (MqGroup mqGroups : values) {
            if (mqGroups.name().equals(group)) {
                isExit = true;
                exitGroup = mqGroups;
                break;
            }
        }
        if (!isExit) {
            throw new MqConfigException("mq current not support group(" + group + ")");
        }

        if (!exitGroup.getTopic().equals(topic)) {
            throw new MqConfigException("mq current not support topic(" + topic + ")");
        }

        if (!exitGroup.getTag().equals(tag)) {
            throw new MqConfigException("mq current not support tag(" + tag + ")");
        }

    }
}
