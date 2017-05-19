package com.zdmoney.message.log.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataDto;
import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.api.dto.log.LogDataSearchDto;
import com.zdmoney.message.log.model.LogData;
import com.zdmoney.zdqd.service.EntityService;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
public interface ILogDataService extends EntityService<LogData> {

    MessageResultDto<LogDataRspDto> storageLog(LogDataReqDto logDataReqDto);

    MessagePageResultDto<LogDataDto> search(LogDataSearchDto searchDto);

    MessageResultDto removeLogData(Date startTime, Date endTime);
}
