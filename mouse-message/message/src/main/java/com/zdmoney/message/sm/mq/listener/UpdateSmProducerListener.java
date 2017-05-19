package com.zdmoney.message.sm.mq.listener;

import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.service.ISmMarketService;
import com.zdmoney.message.sm.service.ISmNotifyService;
import com.zdmoney.message.sm.service.ISmVerifyCodeService;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 更新发送短信的状态
 * Created by Administrator on 2016/11/10.
 */
@Slf4j
public class UpdateSmProducerListener implements MqMessageListener {

    @Autowired
    private ISmMarketService smMarketService;
    @Autowired
    private ISmVerifyCodeService smVerifyCodeService;
    @Autowired
    private ISmNotifyService smNotifyService;

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("UpdateSmProducerListener start at {}", DateUtils.formatCurDate());
        for(MqMessage mqMessage : mqMessages) {
            try {
                if(mqMessage == null) {
                    log.info("UpdateSmProducerListener end 1 at {}", DateUtils.formatCurDate());
                    return true;
                }
                NotifyUpdateMqSmDto notifyUpdateMqSmDto = JsonUtils.fromJson(mqMessage.getData(), NotifyUpdateMqSmDto.class);
                if(notifyUpdateMqSmDto == null) {
                    log.info("UpdateSmProducerListener end 2 sendSmType is empty");
                    return true;
                }
                String logNotifyUpdateMqSmDtoInfo = notifyUpdateMqSmDto.toString();
                log.info("UpdateSmProducerListener start {} " , logNotifyUpdateMqSmDtoInfo);
                SendSmType sendSmType = notifyUpdateMqSmDto.getSendSmType();
                if(sendSmType == null) {
                    log.info("UpdateSmProducerListener end 3 {} sendSmType is empty" , logNotifyUpdateMqSmDtoInfo);
                    return true;
                }
                if(SendSmType.MARKET == sendSmType) {
                    log.info("UpdateSmProducerListener processMsg {} start at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    processMsg(notifyUpdateMqSmDto);
                    log.info("UpdateSmProducerListener processMsg {} end at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    return true;
                } else if(SendSmType.VERIFY == sendSmType) {
                    log.info("UpdateSmProducerListener processVerifyMsg {} start at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    processVerifyMsg(notifyUpdateMqSmDto);
                    log.info("UpdateSmProducerListener processVerifyMsg {} end at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    return true;
                } else if(SendSmType.NOTIFY == sendSmType) {
                    log.info("UpdateSmProducerListener processNotifyMsg {} start at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    processNotifyMsg(notifyUpdateMqSmDto);
                    log.info("UpdateSmProducerListener processNotifyMsg {} end at {}", logNotifyUpdateMqSmDtoInfo, DateUtils.formatCurDate());
                    return true;
                }
            } catch (Exception e){
                log.error("NotifySmMqMessageListener.execute error", e);
                return true;
            }

        }
        log.info("UpdateSmProducerListener end at {}", DateUtils.formatCurDate());
        return true;
    }

    void processMsg(NotifyUpdateMqSmDto notifyUpdateMqSmDto) {
        smMarketService.batchUpdateRetMsg(notifyUpdateMqSmDto);//批量修改
    }

    void processVerifyMsg(NotifyUpdateMqSmDto notifyUpdateMqSmDto) {
        SendSmCommonReqDto sendSmReqDto = notifyUpdateMqSmDto.getSendSmCommonReqDto();
        SendVerifyCodeMsgReqDto sendVerifyCodeMsgReqDto = new SendVerifyCodeMsgReqDto(sendSmReqDto.getMobile(), sendSmReqDto.getVerifyCode(),null);
 /*       SmVerifyCode updateSmVerifyCode = smVerifyCodeService.getRecentSmVerifyCode(sendVerifyCodeMsgReqDto.getMobile(), sendVerifyCodeMsgReqDto.getVerifyCode(), SmSendStatus.SENDING);
        updateSmVerifyCode.setSendStatus(notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmSendStatus());
        updateSmVerifyCode.setSmChannelType(notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmChannelType());
        updateSmVerifyCode.setRetMsg(notifyUpdateMqSmDto.getSendSmCommonRspDto().getThirdText());
        updateSmVerifyCode.setModifyTime(new Date());*/
        smVerifyCodeService.updateVerifyCode(sendVerifyCodeMsgReqDto,notifyUpdateMqSmDto.getSendSmCommonRspDto());
    }

    void processNotifyMsg(NotifyUpdateMqSmDto notifyUpdateMqSmDto) {
        SendSmCommonReqDto sendSmReqDto = notifyUpdateMqSmDto.getSendSmCommonReqDto();
        SendSmNotifyReqDto sendSmNotifyReqDto = new SendSmNotifyReqDto(sendSmReqDto.getMobile(), sendSmReqDto.getSendMsg());
        /*SmNotify smNotify = smNotifyService.getRecentSmNotify(sendSmNotifyReqDto.getMobile(), sendSmNotifyReqDto.getSendMsg(), SmSendStatus.SENDING);
        smNotify.setSendStatus(notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmSendStatus());
        smNotify.setSmChannelType(notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmChannelType());
        smNotify.setRetMsg(notifyUpdateMqSmDto.getSendSmCommonRspDto().getThirdText());
        smNotify.setModifyTime(new Date());*/
        smNotifyService.updateNotifyMsg(sendSmNotifyReqDto,notifyUpdateMqSmDto.getSendSmCommonRspDto());
    }

}
