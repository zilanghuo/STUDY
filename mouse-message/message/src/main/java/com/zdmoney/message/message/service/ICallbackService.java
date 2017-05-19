package com.zdmoney.message.message.service;

import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.mq.client.MqMessage;

import java.util.List;

/**
 * Created by lwf on 2017/5/17.
 */
public interface ICallbackService {

    /**
     * 插入消息通知
     * @param sendDto
     * @return
     */
    boolean sendMessageNotify(List<MqMessage> mqMessages);

    /**
     * 已读消息队列
     * @param mqMessages
     * @return
     */
    boolean readMessageNotify(List<MqMessage> mqMessages);

}
