package com.zdmoney.message.sm.mq.listener;

import com.zdmoney.message.api.dto.sm.SendVerifyCodeMsgReqDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.sm.provider.SmThirdProvider;
import com.zdmoney.message.sm.service.ISmVerifyCodeService;
import com.zdmoney.message.utils.SpringContextHelper;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Slf4j
public class VerifySmMqMessageListener implements MqMessageListener {

    private SmThirdProvider smThirdProvider = (SmThirdProvider) SpringContextHelper.getBean("smThirdProvider");
    private ISmVerifyCodeService smVerifyCodeService = (ISmVerifyCodeService) SpringContextHelper.getBean("smVerifyCodeService");

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("VerifySmMqMessageListener start at "+ DateUtils.formatCurDate());
        for (MqMessage mqMessage : mqMessages) {
            try {
                SendVerifyCodeMsgReqDto sendSmReqDto = JsonUtils.fromJson(mqMessage.getData(), SendVerifyCodeMsgReqDto.class);
                smVerifyCodeService.saveVerifyCodeMsgReq(sendSmReqDto);//保存短信
                //发送验证码短信
                smThirdProvider.sendVerifyCodeMsg(sendSmReqDto);
            }catch (Exception e){
                log.error("VerifySmMqMessageListener.execute error",e);
                return true;
            }
        }
        log.info("VerifySmMqMessageListener end at " + DateUtils.formatCurDate());
        return true;
    }

}
