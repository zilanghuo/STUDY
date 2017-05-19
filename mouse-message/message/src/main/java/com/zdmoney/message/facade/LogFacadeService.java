package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.*;
import com.zdmoney.message.api.facade.ILogFacadeService;
import com.zdmoney.message.log.mq.LogMqProducer;
import com.zdmoney.message.log.service.ILogControlConfigService;
import com.zdmoney.message.log.service.ILogDataService;
import com.zdmoney.message.log.service.ILogDataStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2017/2/4.
 */
@Component
@Slf4j
public class LogFacadeService implements ILogFacadeService {
    @Autowired
    private ILogDataService logDataService;
    @Autowired
    private ILogDataStatService logDataStatService;
    @Autowired
    private ILogControlConfigService logControlConfigService;

    @Autowired
    private LogMqProducer logMqProducer;

    @Override
    public MessageResultDto<LogDataRspDto> accessLog(LogDataReqDto logDataReqDto) {
        return logMqProducer.storageLog(logDataReqDto);
    }

    @Override
    public MessagePageResultDto<LogDataDto> search(LogDataSearchDto searchDto) {
        return logDataService.search(searchDto);
    }

    @Override
    public MessagePageResultDto<LogDataStatDto> searchStat(LogDataStatSearchDto searchDto) {
        if (searchDto.getStatRate() == LogStatRate.DAY)
            return logDataStatService.searchStatDay(searchDto);
        if (searchDto.getStatRate() == LogStatRate.HOUR)
            return logDataStatService.searchStat(searchDto);
        return null;
    }

    @Override
    public MessageResultDto on() {
        return logControlConfigService.on();
    }

    @Override
    public MessageResultDto off() {
        return logControlConfigService.off();
    }
}
