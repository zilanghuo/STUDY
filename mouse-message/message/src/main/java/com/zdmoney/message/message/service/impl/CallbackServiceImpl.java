package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.common.MessageDirection;
import com.zdmoney.message.message.service.ICallbackService;
import com.zdmoney.message.utils.HttpUtils;
import com.zdmoney.message.utils.JackJsonUtil;
import com.zdmoney.mq.client.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lwf on 2017/5/17.
 */
@Slf4j
@Service
public class CallbackServiceImpl implements ICallbackService {

    @Override
    public boolean sendMessageNotify(List<MqMessage> mqMessages) {
        log.info("sendMessageNotify size:{}", mqMessages.size());
        boolean flag = false;
        for (MqMessage message : mqMessages) {
            MsgMessageSendDto sendDto = JsonUtils.fromJson(message.getData(), MsgMessageSendDto.class);
            Map<String, String> sendMap = new HashMap();
            sendMap.put("json", asMessageAddNotifyMap(sendDto));
            log.info("sendMessageNotify json【{}】", asMessageAddNotifyMap(sendDto));
            try {
                StringBuffer stringBuffer = HttpUtils.URLPost(sendDto.getCallbackUrl(), sendMap);
                log.info("sendMessageNotify receive response:【{}】", stringBuffer);
                flag = true;
            } catch (Exception e) {
                log.info("sendMessageNotify error :【{}】,【{}】,again sendMessageNotify", e, e.getMessage());
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean readMessageNotify(List<MqMessage> mqMessages) {
        boolean flag = false;
        for (MqMessage message : mqMessages) {
            MsgMessageReadDto readDto = JsonUtils.fromJson(message.getData(), MsgMessageReadDto.class);
            Map<String, String> sendMap = new HashMap();
            sendMap.put("json", asMessageReadNotifyMap(readDto));
            log.info("readMessageNotify json【{}】",asMessageReadNotifyMap(readDto));
            try {
                StringBuffer stringBuffer = HttpUtils.URLPost(readDto.getCallbackUrl(), sendMap);
                log.info("readMessageNotify receive response:【{}】", stringBuffer);
                flag = true;
            } catch (Exception e) {
                log.info("readMessageNotify error :【{}】,【{}】,again readMessageNotify", e, e.getMessage());
                flag = false;
            }
        }
        return flag;
    }

    //封装 读取消息回调信息
    private String asMessageReadNotifyMap(MsgMessageReadDto readDto) {
        Map<String, String> map = new HashMap();
        map.put("userId", readDto.getUserId());
        map.put("operType", MessageDirection.READ.getValue());
        map.put("messageSize",readDto.getIds().size()+"");
        String str = "";
        try {
            str = JackJsonUtil.objToStr(map);
        } catch (IOException e) {
            log.error(e + e.toString());
        }
        return str;
    }

    //封装插入消息回调信息
    private String asMessageAddNotifyMap(MsgMessageSendDto sendDto) {
        Map<String, String> map = new HashMap();
        map.put("operType", MessageDirection.INSERT.getValue());
        StringBuffer userIds = new StringBuffer();
        for (MsgMessageSendDto.UserDto user : sendDto.getUsers()) {
            userIds.append(user.getUserId()+",");
        }
        map.put("userIds", userIds.substring(0, userIds.length() - 1));
        String str = "";
        try {
            str = JackJsonUtil.objToStr(map);
        } catch (IOException e) {
            log.error(e + e.toString());
        }
        return str;
    }
}
