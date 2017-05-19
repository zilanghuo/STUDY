package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmMarketDAO;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.message.sm.service.ISmMarketService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by gaojc on 2016/11/4.
 */
@Service
public class SmMarketService extends BaseServiceImpl<SmMarket> implements ISmMarketService {
    @Autowired
    private SmMarketDAO smMarketDAO;

    @Override
    public MessagePageResultDto<ShortMsgDto> searchShortMsg(SmSearchDto searchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile","=",searchDto.getMobile())
                .addWithValueQueryParam("batchNo", "=", searchDto.getBatchNo())
                .addWithValueQueryParam("sendStatus", "=", searchDto.getStatus())
                .addWithValueQueryParam("createTime", ">=", searchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", searchDto.getCreateEndDate());
        List<Sort> sortList =
                (CollectionUtils.isEmpty(searchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = countQuery(builder.build());
        List<SmMarket> list = query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
        return new MessagePageResultDto<>(PropertiesUtils.copyListNotExcludeRepeat(ShortMsgDto.class, list)
                , totalSize, searchDto.getPageSize(), searchDto.getPageNo());
    }

    /**
     * @param sendSmReqDto
     * @param type
     * @return
     */
    @Override
    public MessageResultDto saveSmReq(SendSmReqDto sendSmReqDto, SendSmType type) {
        List<String> reqMobiles = sendSmReqDto.getMobiles();
        List<SmMarket> listSms = new ArrayList(reqMobiles.size());
        SmSendStatus smSendStatus = sendSmReqDto.isInstant() ? SmSendStatus.SENDING : SmSendStatus.WAITING;
        for (String mobile : reqMobiles) {
            SmMarket sm = new SmMarket(sendSmReqDto, mobile);
            sm.setSendStatus(smSendStatus);
            sm.setSendSmType(type);
            sm.setCreateTime(new Date());
            sm.setModifyTime(sm.getCreateTime());
            listSms.add(sm);
        }
        smMarketDAO.batchInsert(listSms);
        return MessageResultDto.SUCCESS();
    }

    /**
     * 查询到本次发出的短信记录
     *
     * @param sendSmReqDto
     * @return
     */
    @Override
    public List<SmMarket> getListBySmReqDto(SendSmReqDto sendSmReqDto) {
        Map<String, Object> map = new HashMap();
        map.put("mobiles", sendSmReqDto.getMobiles());
        map.put("batchNo", sendSmReqDto.getBatchNo());
        return smMarketDAO.getListBySmReqDto(map);
    }

    @Override
    public List<SmMarket> getWaitSendSms(Date date, SmSearchDto searchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("sendStatus", "=", SmSendStatus.SENDING.getValue())
                .addWithValueQueryParam("isInstant", "=", 0)
                .addWithValueQueryParam("sendTime", "<=", date);
        List<Sort> sortList =
                (CollectionUtils.isEmpty(searchDto.getSortDtoList()))
                        ? new ArrayList<Sort>() : PropertiesUtils.copyList(Sort.class, searchDto.getSortDtoList());
        sortList.add(new Sort("batchNo", Sort.Direction.ASC));
        return query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
    }

    @Override
    public int getWaitSendSmsCount(Date date) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("sendStatus", "=", SmSendStatus.SENDING.getValue())
                .addWithValueQueryParam("isInstant", "=", 0)
                .addWithValueQueryParam("sendTime", "<=", date);
        return countQuery(builder.build());
    }

    @Override
    public List<StatRecord> statSmMarket(String startTime, String endTime) {
        Map<String, Object> map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return smMarketDAO.statSmMarket(map);
    }

    @Override
    @Transactional
    public Integer batchUpdateRetMsg(NotifyUpdateMqSmDto notifyUpdateMqSmDto) {
        HashMap<String, Object> map = new HashMap<>(5);
        map.put("sendStatus", notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmSendStatus());
        map.put("retMsg", notifyUpdateMqSmDto.getSendSmCommonRspDto().getThirdText());
        map.put("batchNo", notifyUpdateMqSmDto.getSendSmCommonReqDto().getBatchNo());
        map.put("mobiles", notifyUpdateMqSmDto.getSendSmCommonReqDto().getMobiles());
        map.put("isInstant", notifyUpdateMqSmDto.getSendSmCommonReqDto().isInstant());
        map.put("smChannelType", notifyUpdateMqSmDto.getSendSmCommonRspDto().getSmChannelType());
        return smMarketDAO.batchUpdateRetMsg(map);
    }

    @Override
    public Integer batchDelete(List<SmMarket> smMarkets) {
        return smMarketDAO.batchDelete(smMarkets);
    }

    @Override
    public Integer batchUpdate(Date date) {
        return smMarketDAO.batchUpdate(date);
    }

}
