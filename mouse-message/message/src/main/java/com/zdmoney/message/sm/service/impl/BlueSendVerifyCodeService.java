package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonRspDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.pool.SmThirdProviderThreadFactory;
import com.zdmoney.message.sm.service.AbstractBlueSmService;
import com.zdmoney.message.sm.service.ISendSmService;
import com.zdmoney.message.sm.service.ISmVerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/9.
 */
@Slf4j
@Service(value = "blueSendVerifyCodeService")
public class BlueSendVerifyCodeService extends AbstractBlueSmService implements ISendSmService, InitializingBean {
    //发送短信线程池
    private ExecutorService executor;
    @Autowired
    private ISmVerifyCodeService smVerifyCodeService;

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolExecutor(50, 500, 100L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
                new SmThirdProviderThreadFactory("BlueSendVerifyCodeService-pool-"));
    }

    @Override
    public MessageResultDto<SendSmCommonRspDto> sendSm(SendSmCommonReqDto sendSmCommonReqDto) {
        MessageResultDto messageResultDto = smVerifyCodeService.conditionVerifyCode(sendSmCommonReqDto.getMobile());
        if(!messageResultDto.isSuccess()) {
            return messageResultDto;
        }
        blueSendMessage(SmChannelConfig.BLUE_MSG, sendSmCommonReqDto, SendSmType.VERIFY, executor);
        return MessageResultDto.SUCCESS();
    }
}
