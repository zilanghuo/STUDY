package com.zdmoney.message.log.mq.listener;

import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.log.service.ILogControlConfigService;
import com.zdmoney.message.log.service.ILogDataService;
import com.zdmoney.message.utils.CommonUtils;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2017/2/4.
 */
@Slf4j
public class LogDataMqMessageListener implements MqMessageListener {
    @Autowired
    private ILogDataService logDataService;
    @Autowired
    private ILogControlConfigService logCtrlConfigService;

    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        log.info("LogDataMqMessageListener start at {}", DateUtils.formatCurDate());
        for (MqMessage message : mqMessages) {
            try {
                if (!logCtrlConfigService.checkSwitch()) {
                    log.error("logDataConfig switch is closed");
                    return true;
                }
                LogDataReqDto logDataDto = CommonUtils.toBean(message.getData(), LogDataReqDto.class);
                logDataService.storageLog(logDataDto);
            } catch (Exception e) {
                log.error("LogDataMqMessageListener execute error", e);
            }
        }
        log.info("LogDataMqMessageListener end at {}", DateUtils.formatCurDate());
        return true;
    }
}
