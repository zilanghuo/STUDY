package com.zdmoney.message.log.mq;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.message.utils.CommonUtils;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/2/6.
 */
@Slf4j
@Service
public class LogMqProducer implements InitializingBean {

    @Autowired
    @Qualifier("logDataMqProducer")
    private MqProducer mqProducer;

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    /**
     * 日志入库
     *
     * @param logDataReqDto
     * @return
     */
    public MessageResultDto<LogDataRspDto> storageLog(LogDataReqDto logDataReqDto) {
        long startTime = System.currentTimeMillis();
        log.info("LogMqProducer send {} at {}", CommonUtils.toJSONString(logDataReqDto), startTime);
        final MqMessage message = new MqMessage("logDataMqMsg-" + System.currentTimeMillis(),
                CommonUtils.toJSONString(logDataReqDto));
        mqProducer.send(message, new MqSendFailHandler() {
            @Override
            public void execute() {
                msqMqSendFailureService.add(mqProducer, message);
            }
        });
        long endTime = System.currentTimeMillis();
        log.info("LogMqProducer.storageLog end at " + endTime);
        log.info("LogMqProducer.storageLog cost " + (endTime - startTime) + "ms");
        return MessageResultDto.SUCCESS();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
