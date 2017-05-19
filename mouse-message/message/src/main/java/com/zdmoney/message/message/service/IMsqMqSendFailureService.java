package com.zdmoney.message.message.service;

import com.zdmoney.message.message.model.MsqMqSendFailure;
import com.zdmoney.mq.client.MqByteMessage;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by rui on 16/9/21.
 */
public interface IMsqMqSendFailureService extends EntityService<MsqMqSendFailure> {

    /**
     * 保存发送失败，需要重发的消息
     *
     * @param mqProducer
     * @param sendMessage
     */
    void add(MqProducer mqProducer, MqMessage sendMessage);

    /**
     * 保存发送失败，需要重发的消息
     * @param mqAddress
     * @param mqGroup
     * @param mqTopic
     * @param mqTag
     * @param sendMessage
     */
    void add(String mqAddress, String mqGroup, String mqTopic, String mqTag, MqMessage sendMessage);

    /**
     * 保存发送失败，需要重发的消息
     *
     * @param mqProducer
     * @param sendMessage
     */
    void add(MqProducer mqProducer, MqByteMessage sendMessage);

    /**
     * 分页查找待重发信息
     *
     * @param failTime
     * @param interval
     * @return
     */
    List<MsqMqSendFailure> searchFailedMqMsgs(int failTime, int interval);

}
