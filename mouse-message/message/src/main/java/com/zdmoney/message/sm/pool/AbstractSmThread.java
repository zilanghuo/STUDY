package com.zdmoney.message.sm.pool;

import com.bst.msm.http.vo.ResultMsg;
import com.zdmoney.message.api.dto.sm.NotifyUpdateMqSmDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonRspDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/11/21.
 */
@NoArgsConstructor
@Slf4j
public abstract class AbstractSmThread implements Runnable {

    @Autowired
    protected MqProducer updateSmMqProducer = (MqProducer) SpringContextHelper.getBean("updateSmMqProducer");
    protected SmChannelConfig config;
    protected SendSmCommonReqDto sendSmReqDto;
    protected SendSmType sendSmType;

    public AbstractSmThread(SendSmCommonReqDto sendSmReqDto, SmChannelConfig config, SendSmType sendSmType) {
        this.sendSmReqDto = sendSmReqDto;
        this.config = config;
        this.sendSmType = sendSmType;
    }

    protected abstract SendSmCommonRspDto sendMsg() throws Exception;

    @Override
    public void run() {
        SendSmCommonRspDto sendSmRspDto;
        try {
            sendSmRspDto = sendMsg();
        } catch (Exception e) {
            log.error("AbstractSmThread send message {} error {}", sendSmReqDto.toString(), e);
            sendSmRspDto = SendSmCommonRspDto.FAIL();
            sendSmRspDto.setSmChannelType(config.getSmChannelType());
            sendSmRspDto.setThirdText(e.getMessage());
        }
        String thirdText = sendSmRspDto.getThirdText();
        if(thirdText.length() > 1000){
            sendSmRspDto.setThirdText(StringUtils.substring(thirdText, 0, 1000));
        }
        NotifyUpdateMqSmDto notifyUpdateMqSmDto = new NotifyUpdateMqSmDto(sendSmReqDto, sendSmRspDto, sendSmType);
        notifyResult(notifyUpdateMqSmDto);
    }

    public abstract SendSmCommonRspDto parseResult(ResultMsg resultMsg, String rs);

    /**
     * 通知短信修改队列
     */
    public void notifyResult(NotifyUpdateMqSmDto notifyUpdateMqSmDto) {
        MqMessage mqMessage = new MqMessage("updateMessage_" + System.currentTimeMillis(), JsonUtils.toJson(notifyUpdateMqSmDto));
        updateSmMqProducer.send(mqMessage);
    }

}
