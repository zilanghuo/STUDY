package com.zdmoney.message.log.service.impl;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.api.dto.log.LogDataStatDto;
import com.zdmoney.message.api.dto.log.LogDataStatSearchDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.log.dao.LogDataStatDAO;
import com.zdmoney.message.log.dao.LogDataStatDayDAO;
import com.zdmoney.message.log.model.LogDataStat;
import com.zdmoney.message.log.model.LogDataStatDay;
import com.zdmoney.message.log.model.LogStatWrapRecord;
import com.zdmoney.message.api.dto.log.LogStatRate;
import com.zdmoney.message.log.service.ILogDataStatService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by user on 2017/2/4.
 */
@Service
@Slf4j
public class LogDataStatService extends BaseServiceImpl<LogDataStat> implements ILogDataStatService {
    @Autowired
    private LogDataStatDAO logDataStatDAO;
    @Autowired
    private LogDataStatDayDAO logDataStatDayDAO;

    @Override
    @Transactional
    public MessageResultDto<LogDataRspDto> logStat(Date startTime, Date endTime, LogStatRate statRate) {
        Map<String, Object> map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<LogStatWrapRecord> statLogData = logDataStatDAO.getStatLogData(map);
        if (CollectionUtils.isEmpty(statLogData)) {
            return MessageResultDto.FAIL("no log stat record");
        }
        Date date = new Date();
        if (statRate.equals(LogStatRate.HOUR)) {
            List<LogDataStat> statList = PropertiesUtils.copyListNotExcludeRepeat(LogDataStat.class, statLogData);
            for (LogDataStat stat : statList) {
                stat.setStatDate(startTime);
                stat.setCreateTime(date);
                stat.setModifyTime(date);
                stat.setOperator(MessageOperator.SYS.name());
            }
            logDataStatDAO.insertLogStat(statList);
        } else {
            List<LogDataStatDay> statDayList = PropertiesUtils.copyListNotExcludeRepeat(LogDataStatDay.class, statLogData);
            for (LogDataStatDay stat : statDayList) {
                stat.setStatDate(startTime);
                stat.setCreateTime(date);
                stat.setModifyTime(date);
                stat.setOperator(MessageOperator.SYS.name());
            }
            logDataStatDayDAO.insertLogStatDay(statDayList);
        }

        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessagePageResultDto<LogDataStatDto> searchStat(LogDataStatSearchDto reqDto) {
        QueryParamBuilder builder = getLogStatSearchBuilder(reqDto);
        List<Sort> sortList = getLogStatSearchSorts(reqDto);
        int totalSize = countQuery(builder.build());
        List<LogDataStat> logDataStatList = query(builder.build(), sortList, reqDto.getStart(), reqDto.getPageSize());
        List<LogDataStatDto> statDtos = PropertiesUtils.copyListNotExcludeRepeat(LogDataStatDto.class, logDataStatList);
        for (int i = 0; i < logDataStatList.size(); i++) {
            statDtos.get(i).setStatDate(DateUtils.format(logDataStatList.get(i).getStatDate()));
        }
        return new MessagePageResultDto<>(statDtos, totalSize, reqDto.getPageSize(), reqDto.getPageNo());
    }

    @Override
    public MessagePageResultDto<LogDataStatDto> searchStatDay(LogDataStatSearchDto reqDto) {
        QueryParamBuilder builder = getLogStatSearchBuilder(reqDto);
        List<Sort> sortList = getLogStatSearchSorts(reqDto);
        int totalSize = logDataStatDayDAO.countQuery(builder.build());
        List<LogDataStatDay> logDataStatList = logDataStatDayDAO.query(builder.build(), sortList, reqDto.getStart(), reqDto.getPageSize());
        List<LogDataStatDto> statDtos = PropertiesUtils.copyListNotExcludeRepeat(LogDataStatDto.class, logDataStatList);
        for (int i = 0; i < logDataStatList.size(); i++) {
            statDtos.get(i).setStatDate(DateUtils.format(logDataStatList.get(i).getStatDate()));
        }
        return new MessagePageResultDto<>(statDtos, totalSize, reqDto.getPageSize(), reqDto.getPageNo());
    }

    private List<Sort> getLogStatSearchSorts(LogDataStatSearchDto reqDto) {
        List<Sort> sortList =
                (CollectionUtils.isEmpty(reqDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, reqDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        sortList.add(new Sort("avgSpendTime", Sort.Direction.DESC));
        return sortList;
    }

    private QueryParamBuilder getLogStatSearchBuilder(LogDataStatSearchDto reqDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("statDate", ">=", reqDto.getStartDate())
                .addWithValueQueryParam("statDate", "<=", reqDto.getEndDate())
                .addWithValueQueryParam("systemName", StringUtils.isNotEmpty(reqDto.getSysName()) ? "=" : null,
                        reqDto.getSysName())
                .addWithValueQueryParam("methodName", StringUtils.isNotEmpty(reqDto.getMethodName()) ? "=" : null,
                        reqDto.getMethodName());
        return builder;
    }
}
