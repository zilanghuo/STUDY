package com.zdmoney.message.log.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataDto;
import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.api.dto.log.LogDataSearchDto;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.log.dao.LogDataDAO;
import com.zdmoney.message.log.model.LogData;
import com.zdmoney.message.log.service.ILogDataService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by user on 2017/2/4.
 */
@Service
public class LogDataService extends BaseServiceImpl<LogData> implements ILogDataService {
    @Autowired
    private LogDataDAO logDataDAO;

    @Override
    @Transactional
    public MessageResultDto<LogDataRspDto> storageLog(LogDataReqDto logDataReqDto) {
        LogData logData = new LogData(logDataReqDto);
        logDataDAO.insert(logData);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessagePageResultDto<LogDataDto> search(LogDataSearchDto reqDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("startTime", ">=", reqDto.getStartTime())
                .addWithValueQueryParam("endTime", "<=", reqDto.getEndTime())
                .addWithValueQueryParam("systemName", "=" , reqDto.getSystemName())
                .addWithValueQueryParam("methodName", "=" , reqDto.getMethodName());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(reqDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, reqDto.getSortDtoList());
        sortList.add(new Sort("spendTime", Sort.Direction.DESC));
        int totalSize = countQuery(builder.build());
        List<LogData> logDataList = query(builder.build(), sortList, reqDto.getStart(), reqDto.getPageSize());
        List<LogDataDto> logDataDtos = PropertiesUtils.copyListNotExcludeRepeat(LogDataDto.class, logDataList);
        for(LogDataDto dto:logDataDtos){
            String acesMsg = dto.getAccessMsg();
            dto.setAccessMsg(acesMsg.substring(0,acesMsg.length()>100?100:acesMsg.length()));
        }
        return new MessagePageResultDto<>(logDataDtos, totalSize, reqDto.getPageSize(), reqDto.getPageNo());
    }

    @Override
    @Transactional
    public MessageResultDto removeLogData(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        logDataDAO.removeLogData(map);
        return MessageResultDto.SUCCESS();
    }

}
