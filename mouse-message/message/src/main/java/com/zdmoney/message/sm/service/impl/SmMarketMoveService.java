package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.sm.model.*;
import com.zdmoney.message.sm.service.*;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */
@Service
public class SmMarketMoveService implements ISmMarketMoveService {
    @Autowired
    private ISmMarketService smMarketService;
    @Autowired
    private ISmHistoryService smHistoryService;
    @Autowired
    private ISmVerifyCodeService smVerifyCodeService;
    @Autowired
    private ISmVerifyCodeHistoryService smVerifyCodeHistoryService;
    @Autowired
    private ISmNotifyService smNotifyService;
    @Autowired
    private ISmNotifyHistoryService smNotifyHistoryService;

    private final static Integer PER_MOVE_SIZE = 2000;

    @Override
    @Transactional
    public MessageResultDto moveSm(Date startTime, Date endTime) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("modifyTime", ">=", startTime);
        builder.addWithValueQueryParam("modifyTime", "<=", endTime);
        int totalCount = smMarketService.countQuery(builder.build());
        int page = totalCount % PER_MOVE_SIZE == 0 ? totalCount / PER_MOVE_SIZE : totalCount / PER_MOVE_SIZE + 1;
        for(int i=1; i<=page; i++) {
            List<SmMarket> smList = smMarketService.query(builder.build(), null, (page -i) * PER_MOVE_SIZE, PER_MOVE_SIZE);
            if(CollectionUtils.isEmpty(smList)) {
                continue;
            }
            List<SmMarketHistory> smHistoryList = PropertiesUtils.copyListNotExcludeRepeat(SmMarketHistory.class, smList);
            smHistoryService.batchInsert(smHistoryList);
            smMarketService.batchDelete(smList);
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    @Transactional
    public MessageResultDto moveSmVerifyCode(Date startTime, Date endTime) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("modifyTime", ">=", startTime);
        builder.addWithValueQueryParam("modifyTime", "<=", endTime);
        int totalCount = smVerifyCodeService.countQuery(builder.build());
        int page = totalCount % PER_MOVE_SIZE == 0 ? totalCount / PER_MOVE_SIZE : totalCount / PER_MOVE_SIZE + 1;

        for(int i=1; i<=page; i++) {
            List<SmVerifyCode> smVerifyCodeList = smVerifyCodeService.query(builder.build(), null, (page -i) * PER_MOVE_SIZE, PER_MOVE_SIZE);
            if(CollectionUtils.isEmpty(smVerifyCodeList)) {
                continue;
            }
            List<SmVerifyCodeHistory> smVerifyCodeHistoryList = PropertiesUtils.copyListNotExcludeRepeat(SmVerifyCodeHistory.class, smVerifyCodeList);
            smVerifyCodeHistoryService.batchInsert(smVerifyCodeHistoryList);
            smVerifyCodeService.batchDelete(smVerifyCodeList);
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    @Transactional
    public MessageResultDto moveSmNotify(Date startTime, Date endTime) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("modifyTime", ">=", startTime);
        builder.addWithValueQueryParam("modifyTime", "<=", endTime);
        int totalCount = smNotifyService.countQuery(builder.build());
        int page = totalCount % PER_MOVE_SIZE == 0 ? totalCount / PER_MOVE_SIZE : totalCount / PER_MOVE_SIZE + 1;

        for(int i=1; i<=page; i++) {
            List<SmNotify> smNotifies = smNotifyService.query(builder.build(), null, (page -i) * PER_MOVE_SIZE, PER_MOVE_SIZE);
            if(CollectionUtils.isEmpty(smNotifies)) {
                continue;
            }
            List<SmNotifyHistory> smNotifyHistories = PropertiesUtils.copyListNotExcludeRepeat(SmNotifyHistory.class, smNotifies);
            smNotifyHistoryService.batchInsert(smNotifyHistories);
            smNotifyService.batchDelete(smNotifies);
        }
        return MessageResultDto.SUCCESS();
    }

}
