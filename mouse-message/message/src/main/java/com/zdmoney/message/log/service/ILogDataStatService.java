package com.zdmoney.message.log.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.api.dto.log.LogDataStatDto;
import com.zdmoney.message.api.dto.log.LogDataStatSearchDto;
import com.zdmoney.message.api.dto.log.LogStatRate;

import java.util.Date;

/**
 * Created by user on 2017/2/4.
 */
public interface ILogDataStatService {

    MessageResultDto<LogDataRspDto> logStat(Date startTime, Date endTime, LogStatRate statRate);

    MessagePageResultDto<LogDataStatDto> searchStat(LogDataStatSearchDto searchDto);

    MessagePageResultDto<LogDataStatDto> searchStatDay(LogDataStatSearchDto searchDto);
}
