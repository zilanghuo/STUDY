package com.zdmoney.message.message.model;

import com.zdmoney.message.common.base.BaseEntity;
import com.zdmoney.mq.client.MqByteMessage;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

/**
 * Created by rui on 16/9/21.
 * 消息队列发送失败信息
 */
@Data
@NoArgsConstructor
@Table(name = "T_MSG_MQ_SEND_FAILURE")
public class MsqMqSendFailure extends BaseEntity {

    private String mqAddress;

    private String mqGroup;

    private String mqTopic;

    private String mqTag;

    private String messageId;

    private String messageContent;

    private Integer sendFailTimes; //1

    private Integer sendStatus; //0失败，1成功


    public MsqMqSendFailure(MqProducer mqProducer, MqByteMessage sendMessage) {
        this.mqAddress = mqProducer.getMqConfig().getAddress();
        this.mqGroup = mqProducer.getGroup();
        this.mqTopic = mqProducer.getTopic();
        this.mqTag = mqProducer.getTag();
        this.messageId = sendMessage.getKey();
        this.messageContent = new String(sendMessage.getData());
        this.sendFailTimes = 1;
        this.sendStatus = 0;
        this.createTime = new Date();
        this.modifyTime = createTime;
    }

    public MsqMqSendFailure(MqProducer mqProducer, MqMessage sendMessage) {
        this.mqAddress = mqProducer.getMqConfig().getAddress();
        this.mqGroup = mqProducer.getGroup();
        this.mqTopic = mqProducer.getTopic();
        this.mqTag = mqProducer.getTag();
        this.messageId = sendMessage.getKey();
        this.messageContent = sendMessage.getData();
        this.sendFailTimes = 1;
        this.sendStatus = 0;
        this.createTime = new Date();
        this.modifyTime = createTime;
    }


    public MsqMqSendFailure(String mqAddress, String mqGroup, String mqTopic, String mqTag, MqMessage sendMessage) {
        this.mqAddress = mqAddress;
        this.mqGroup = mqGroup;
        this.mqTopic = mqTopic;
        this.mqTag = mqTag;
        this.messageId = sendMessage.getKey();
        this.messageContent = sendMessage.getData();
        this.sendFailTimes = 1;
        this.sendStatus = 0;
        this.createTime = new Date();
        this.modifyTime = createTime;
    }
}
