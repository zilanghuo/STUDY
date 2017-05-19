package com.zdmoney.message.sm.mq;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.SendSmNotifyReqDto;
import com.zdmoney.message.api.dto.sm.SendSmReqDto;
import com.zdmoney.message.api.dto.sm.SendVerifyCodeMsgReqDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.JsonUtils;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/9.
 * 发送短信放入队列
 */
@Slf4j
@Service
public class SmMqProducer implements InitializingBean {

    @Autowired
    @Qualifier("verifyMsgMqProducer")
    private MqProducer verifyMsgMqProducer;

    @Autowired
    @Qualifier("marketSmMqProducer")
    private MqProducer marketSmMqProducer;

    @Autowired
    @Qualifier("notifySmMqProducer")
    private MqProducer notifySmMqProducer;

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    public MessageResultDto sendVerifyCodeMsg(SendVerifyCodeMsgReqDto sendDto) {
        log.info("SmMqProducer sendVerifyCodeMsg start at " + getLogFormatTime());
        putVerifyCodeSmMq(sendDto);//放入验证码队列
        log.info("SmMqProducer sendVerifyCodeMsg end at " + getLogFormatTime());
        return MessageResultDto.SUCCESS();
    }

    private String getLogFormatTime() {
        return DateUtils.format(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:SSS");
    }

    public MessageResultDto sendMarketMsg(SendSmReqDto sendDto) {
        log.info("SmMqProducer sendMarketMsg start at " + getLogFormatTime());
        putSmMarketMq(sendDto);
        log.info("SmMqProducer sendMarketMsg end at " + getLogFormatTime());
        return MessageResultDto.SUCCESS();
    }

    public MessageResultDto sendNotifyMsg(final SendSmNotifyReqDto sendDto) {
        log.info("SmMqProducer sendNotifyMsg start at " + getLogFormatTime());
        putNotifySmMq(sendDto);//放入通知短信队列
        log.info("SmMqProducer sendNotifyMsg end at " + getLogFormatTime());
        return MessageResultDto.SUCCESS();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private void putNotifySmMq(SendSmNotifyReqDto sendDto) {
        final MqMessage mqMessage = new MqMessage("notifyMqMessage_" + System.currentTimeMillis(), JsonUtils.toJson(sendDto));
        notifySmMqProducer.send(mqMessage, new MqSendFailHandler() {
            @Override
            public void execute() {
                msqMqSendFailureService.add(notifySmMqProducer, mqMessage);
            }
        });

    }

    /**
     * 立即执行一个,放入营销短信队列
     * @param sendDto
     */
    private void putSmMarketMq(SendSmReqDto sendDto) {
        final MqMessage mqMessage = new MqMessage("marketMqMessage_" + System.currentTimeMillis(), JsonUtils.toJson(sendDto));
        marketSmMqProducer.send(mqMessage, new MqSendFailHandler() {
            @Override
            public void execute() {
                msqMqSendFailureService.add(marketSmMqProducer, mqMessage);
            }
        });
    }

    /**
     * 立即执行一个,放入验证码短信队列
     */
    private void putVerifyCodeSmMq(SendVerifyCodeMsgReqDto sendDto) {
        final MqMessage mqMessage = new MqMessage("verifyMqMessage_" + System.currentTimeMillis(), JsonUtils.toJson(sendDto));
        verifyMsgMqProducer.send(mqMessage, new MqSendFailHandler() {
            @Override
            public void execute() {
                msqMqSendFailureService.add(verifyMsgMqProducer, mqMessage);
            }
        });
    }

}
