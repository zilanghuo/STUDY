package com.zdmoney.message.api.facade;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.mq.client.MqMessage;

/**
 * MQ 消息队列服务
 * Created by gaojc on 2016/9/22.
 */
public interface IMsgMqFacadeService {
    /**
     * 消息队列发送消息重试
     *
     * @param mqAddress mq的nameServer地址
     * @param mqGroup
     * @param mqTopic
     * @param mqTag
     * @param sendMessage 待发送的消息
     * @return
     */
    MessageResultDto<Boolean> reSend(String mqAddress, String mqGroup,
                                     String mqTopic, String mqTag, MqMessage sendMessage);
}
