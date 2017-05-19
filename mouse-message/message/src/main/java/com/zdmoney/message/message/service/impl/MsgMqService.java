package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.message.service.IMsgMqService;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.group.MqGroup;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by gaojc on 2016/9/22.
 */
@Slf4j
@Service
public class MsgMqService implements IMsgMqService {

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    @Autowired
    @Qualifier("msgAddNotifyMqProducer")
    private MqProducer msgAddNotifyMqProducer;

    @Autowired
    @Qualifier("msgReadNotifyMqProducer")
    private MqProducer msgReadNotifyMqProducer;

    @Value("${msg.mq.serverAddress}")
    private String mqAddress;

    @Override
    public MessageResultDto<Boolean> add(String mqAddress, String mqGroup, String mqTopic, String mqTag, MqMessage sendMessage) {
        msqMqSendFailureService.add(mqAddress, mqGroup, mqTopic, mqTag, sendMessage);
        return MessageResultDto.SUCCESS();
    }


    @Override
    public MessageResultDto<String> putAddMessageNotify(final MsgMessageSendDto sendDto, final MqGroup mqGroup) {
        //使用队列
        log.info("putAddMessageNotify start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MqMessage message = new MqMessage(mqGroup.name() + sendDto.getMerchantSerialNo(), JsonUtils.toJson(sendDto));
                msgAddNotifyMqProducer.send(message, new MqSendFailHandler() {
                    @Override
                    public void execute() {
                        log.error("发送异常");
                        add(mqAddress, mqGroup.name(), mqGroup.getTopic(), mqGroup.getTag(), message);
                    }
                });
            }
        }).start();
        log.info("putAddMessageNotify end");
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto<String> putReadMessageNotify(final MsgMessageReadDto readDto, final MqGroup mqGroup) {
        //使用队列
        log.info("putReadMessageNotify start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MqMessage message = new MqMessage(mqGroup.name() + readDto.getIds().toString(), JsonUtils.toJson(readDto));
                msgReadNotifyMqProducer.send(message, new MqSendFailHandler() {
                    @Override
                    public void execute() {
                        log.error("发送异常");
                        add(mqAddress, mqGroup.name(), mqGroup.getTopic(), mqGroup.getTag(), message);
                    }
                });
            }
        }).start();
        log.info("putReadMessageNotify end");
        return MessageResultDto.SUCCESS();

    }
}
