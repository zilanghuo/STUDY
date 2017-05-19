package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmNotifyDAO;
import com.zdmoney.message.sm.model.SmNotify;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.message.sm.service.ISmNotifyService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 通知短信服务
 * Created by gaojc on 2016/11/12.
 */
@Slf4j
@Service
public class SmNotifyService extends BaseServiceImpl<SmNotify> implements ISmNotifyService {

    @Autowired
    private SmNotifyDAO smNotifyDAO;

    @Override
    public MessagePageResultDto<SmNotifyDto> searchSmNotify(SmSearchDto searchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile","=",searchDto.getMobile())
                .addWithValueQueryParam("sendStatus", "=", searchDto.getStatus())
                .addWithValueQueryParam("createTime", ">=", searchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", searchDto.getCreateEndDate());
        List<Sort> sortList = new ArrayList(1);
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = countQuery(builder.build());
        List<SmNotify> list = query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
        return new MessagePageResultDto<>(PropertiesUtils.copyListNotExcludeRepeat(SmNotifyDto.class, list)
                , totalSize, searchDto.getPageSize(), searchDto.getPageNo());

    }

    @Override
    public MessageResultDto saveNotifyMsgReq(SendSmNotifyReqDto sendDto) {
        SmNotify smNotify=new SmNotify(sendDto);
        smNotify.setSendStatus(SmSendStatus.SENDING);
        insert(smNotify);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public SmNotify getRecentSmNotify(String mobile, String sendMsg, SmSendStatus smSendStatus) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile", "=", mobile)
                .addWithValueQueryParam("sendMsg", "=", sendMsg)
                .addWithValueQueryParam("sendStatus", "=", smSendStatus.getValue());//状态为发送状态
        List<Sort> sortList = new ArrayList(1);
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        List<SmNotify> smNotifies = query(builder.build(), sortList);
        if (null == smNotifies || smNotifies.size() < 0) {
            return null;
        }
        return smNotifies.get(0);
    }

    @Override
    public List<StatRecord> statSmNotify(String startTime, String endTime) {
        Map<String, Object> map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return smNotifyDAO.statSmNotify(map);
    }

    @Override
    public void batchDelete(List<SmNotify> smNotifies) {
        smNotifyDAO.batchDelete(smNotifies);
    }

    @Override
    public MessageResultDto updateNotifyMsg(SendSmNotifyReqDto sendDto, SendSmCommonRspDto rspDto) {
        SmNotify smNotify = new SmNotify(sendDto);
        smNotify.setMobile(sendDto.getMobile());
        smNotify.setSendMsg(sendDto.getSendMsg());
        smNotify.setSmChannelType(rspDto.getSmChannelType());
        smNotify.setRetMsg(rspDto.getThirdText());
        smNotify.setSendStatus(rspDto.getSmSendStatus());
        smNotify.setModifyTime(new Date());

        Integer updatedRowCount = smNotifyDAO.updateSmNotify(smNotify);
        if(updatedRowCount>0)
            return MessageResultDto.SUCCESS();
        String errMsg="SmNotifyService updateNotifyMsg fail";
        log.error(errMsg);
        return MessageResultDto.FAIL(errMsg);
    }
}
