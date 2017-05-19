package com.zdmoney.message.message.service;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.group.MqGroup;

/**
 * Created by gaojc on 2016/9/22.
 */
public interface IMsgMqService {
    /**
     * 消息队列发送失败重试
     *
     * @param mqAddress
     * @param mqGroup
     * @param mqTopic
     * @param mqTag
     * @param sendMessage
     * @return
     */
    MessageResultDto<Boolean> add(String mqAddress, String mqGroup, String mqTopic, String mqTag, MqMessage sendMessage);

    /**存放插入消息的队列
     * @param sendDto
     * @param mqGroup
     * @return
     */
    MessageResultDto<String> putAddMessageNotify(MsgMessageSendDto sendDto, MqGroup mqGroup);

    /**
     * 存放 已读消息队列
     * @param messages
     * @param mqGroup
     * @return
     */
    MessageResultDto<String> putReadMessageNotify(MsgMessageReadDto readDto,MqGroup mqGroup);
}
