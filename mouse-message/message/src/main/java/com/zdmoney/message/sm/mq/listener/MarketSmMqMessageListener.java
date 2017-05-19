package com.zdmoney.message.sm.mq.listener;

import com.zdmoney.message.api.dto.sm.SendSmReqDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.provider.SmThirdProvider;
import com.zdmoney.message.sm.service.ISmMarketService;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Slf4j
public class MarketSmMqMessageListener implements MqMessageListener {

    private SmThirdProvider smThirdProvider = (SmThirdProvider) SpringContextHelper.getBean("smThirdProvider");
    private ISmMarketService smMarketService = (ISmMarketService) SpringContextHelper.getBean("smMarketService");

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("MarketSmMqMessageListener start at "+ DateUtils.formatCurDate());
        for (MqMessage mqMessage : mqMessages) {
            try {
                SendSmReqDto sendMsgDto = JsonUtils.fromJson(mqMessage.getData(), SendSmReqDto.class);
                log.info("MqMessage SendSmReqDto.batchNo=" + sendMsgDto.getBatchNo());
                //保存短信状态
                smMarketService.saveSmReq(sendMsgDto, SendSmType.MARKET);
                //发送短信
                if (sendMsgDto.isInstant()) {
                    smThirdProvider.sendMarketMsg(sendMsgDto);
                    return true;
                }
            } catch (Exception e){
                log.error("NotifySmMqMessageListener.execute error",e);
                return true;
            }
        }
        log.info("MarketSmMqMessageListener end at "+ DateUtils.formatCurDate());
        return true;
    }
}
