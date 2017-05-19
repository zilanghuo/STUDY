package com.zdmoney.message.sm.mq.listener;

import com.zdmoney.message.api.dto.sm.SendSmNotifyReqDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.provider.SmThirdProvider;
import com.zdmoney.message.sm.service.ISmNotifyService;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Slf4j
public class NotifySmMqMessageListener implements MqMessageListener {

    private SmThirdProvider smThirdProvider = (SmThirdProvider) SpringContextHelper.getBean("smThirdProvider");
    private ISmNotifyService smNotifyService=(ISmNotifyService)SpringContextHelper.getBean("smNotifyService");
    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("NotifySmMqMessageListener start at "+ DateUtils.formatCurDate());
        for (MqMessage mqMessage : mqMessages) {
            try {
                SendSmNotifyReqDto sendSmNotifyReqDto = JsonUtils.fromJson(mqMessage.getData(), SendSmNotifyReqDto.class);
                smNotifyService.saveNotifyMsgReq(sendSmNotifyReqDto);//保存通知短信
                //发送通知短信
                smThirdProvider.sendNotifySm(sendSmNotifyReqDto);
            }catch (Exception e){
                log.error("NotifySmMqMessageListener.execute error",e);
                return true;
            }
        }
        log.info("NotifySmMqMessageListener end at "+ DateUtils.formatCurDate());
        return true;
    }
}
