package com.zdmoney.message.message.service.listener;

import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.message.service.ICallbackService;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lwf on 2016/9/1.
 */
@Slf4j
public class MsgAddNotifyMqMessageLister implements MqMessageListener {

    @Autowired
    private ICallbackService callbackService;

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("MsgAddNotifyMqMessageLister execute start:" + DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:SSS"));
        try {
            return callbackService.sendMessageNotify(mqMessages);
        } catch (Exception e) {
            log.error("MsgMessageMqMessageLister execute exception:", e);
            return false;
        }
    }
}
