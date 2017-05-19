package com.zdmoney.message.message.service.listener;

import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.message.model.MsgMessage;
import com.zdmoney.message.message.service.IMsgMessageService;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwf on 2016/9/1.
 */
@Slf4j
public class MsgMessageMqMessageLister implements MqMessageListener {

    private IMsgMessageService msgMessageService = (IMsgMessageService) SpringContextHelper.getBean("msgMessageService");

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("MsgMessageMqMessageLister execute start:" + DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:SSS"));
        try {
            sendMessages(mqMessages);
            log.info("MsgMessageMqMessageLister execute end:" + DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:SSS"));
        } catch (Exception e) {
            log.error("MsgMessageMqMessageLister execute exception:",e);
        }
        return true;
    }

    private void sendMessages(List<MqMessage> mqMessages) {
        for (MqMessage mqMessage : mqMessages) {
            MsgMessageSendDto sendDto = JsonUtils.fromJson(mqMessage.getData(), MsgMessageSendDto.class);
            long starTime = System.currentTimeMillis();
            String threadName = Thread.currentThread().getName();
            log.info(threadName + " begin time=" + DateUtils.format(starTime, "yyyy-MM-dd hh:mm:ss:SSS"));
            List<MsgMessage> messages = new ArrayList<MsgMessage>();
            List<MsgMessageSendDto.UserDto> users = sendDto.getUsers();
            for (MsgMessageSendDto.UserDto user : users) {
                MsgMessage message = new MsgMessage(sendDto);
                if (isPhoneSize(user.getUserPhone())) {
                    message.setUserId(user.getUserId());
                    message.setUserNo(user.getUserNo());
                    message.setUserName(user.getUserName() == null ? "" : user.getUserName());
                    message.setUserPhone(user.getUserPhone());
                    messages.add(message);
                }
            }
            msgMessageService.addMessages(messages);
            log.info("msgMessageService add succeed ,size：" + messages.size());
            long endTime = System.currentTimeMillis();
            log.info(threadName + " end time=" + DateUtils.format(endTime, "yyyy-MM-dd hh:mm:ss:SSS"));
            log.info(threadName + " cost time=" + (endTime - starTime));
            //插入回调通知的队列
            msgMessageService.sendNotifyToMq(sendDto);
        }
    }

    private static Boolean isPhoneSize(String phone) {
        if (phone != null && phone.length() == 11) {
            return true;
        }
        log.info("手机格式不符合，手机号为：" + phone);
        return false;
    }
}
