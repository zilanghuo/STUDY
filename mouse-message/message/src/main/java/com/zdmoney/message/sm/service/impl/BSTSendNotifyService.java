package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmCommonRspDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.pool.SmThirdProviderThreadFactory;
import com.zdmoney.message.sm.service.AbstractBstSmService;
import com.zdmoney.message.sm.service.ISendSmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/12/9.
 */
@Slf4j
@Service(value = "bstSendNotifyService")
public class BSTSendNotifyService extends AbstractBstSmService implements ISendSmService, InitializingBean {
    //发送短信线程池
    private ExecutorService executor;

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolExecutor(50, 500, 100L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
                new SmThirdProviderThreadFactory("BSTSendNotifyService-pool-"));
    }

    @Override
    public MessageResultDto<SendSmCommonRspDto> sendSm(SendSmCommonReqDto sendSmCommonReqDto) {
        bstSendMessage(SmChannelConfig.BST_MSG, sendSmCommonReqDto, SendSmType.NOTIFY, executor);
        return MessageResultDto.SUCCESS();
    }
}