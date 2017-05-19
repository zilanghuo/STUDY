package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.utils.PropertiesUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmVerifyCodeDAO;
import com.zdmoney.message.sm.model.SmVerifyCode;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.message.sm.service.ISmVerifyCodeService;
import com.zdmoney.zdqd.dao.complexQuery.QueryParamBuilder;
import com.zdmoney.zdqd.dao.complexQuery.Sort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 验证码 服务
 * Created by gaojc on 2016/11/10.
 */
@Service
@Slf4j
public class SmVerifyCodeService extends BaseServiceImpl<SmVerifyCode> implements ISmVerifyCodeService {
    @Autowired
    private SmVerifyCodeDAO smVerifyCodeDAO;

    private final static Integer PER_MIN_NUMBER = 3;//每分钟最多3次

    @Value("${verifyCode.effect.period}")
    private int effectPeriod;

    @Override
    public MessagePageResultDto<VerifyCodeRspDto> searchVerifyCode(SmSearchDto searchDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile", "=",searchDto.getMobile())
                .addWithValueQueryParam("sendStatus", "=", searchDto.getStatus())
                .addWithValueQueryParam("createTime", ">=", searchDto.getCreateStartDate())
                .addWithValueQueryParam("createTime", "<=", searchDto.getCreateEndDate());
        List<Sort> sortList = new ArrayList(1);
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        int totalSize = smVerifyCodeDAO.countQuery(builder.build());
        List<SmVerifyCode> list = smVerifyCodeDAO.query(builder.build(), sortList, searchDto.getStart(), searchDto.getPageSize());
        return new MessagePageResultDto<>(PropertiesUtils.copyListNotExcludeRepeat(VerifyCodeRspDto.class, list)
                , totalSize, searchDto.getPageSize(), searchDto.getPageNo());
    }

    //10分钟以内存在，认为有效
    @Override
    public MessageResultDto verificationCode(VerifyCodeReqDto reqDto) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile", "=", reqDto.getMobile())
                .addWithValueQueryParam("verifyCode", "=", reqDto.getVerifyCode())
                .addWithValueQueryParam("createTime", ">=", DateUtils.plusMinutes(new Date(), -effectPeriod));

        int size = smVerifyCodeDAO.countQuery(builder.build());
        if (size != 1) {
            return MessageResultDto.FAIL("验证码错误");
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    public MessageResultDto conditionVerifyCode(String mobile) {
        Date date = new Date();
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        //1分钟以内存在
        builder.addWithValueQueryParam("mobile", "=", mobile)
                .addWithValueQueryParam("createTime", ">=", DateUtils.plusMinutes(date, -1))
                .addWithValueQueryParam("createTime", "<=", date);
        int size = smVerifyCodeDAO.countQuery(builder.build());
        if (size > PER_MIN_NUMBER) {
            return MessageResultDto.FAIL("该手机号码次数超过限制");
        }
        return MessageResultDto.SUCCESS();
    }

    @Override
    public SmVerifyCode getRecentSmVerifyCode(String mobile, String verifyCode, SmSendStatus smSendStatus) {
        QueryParamBuilder builder = QueryParamBuilder.newBuilder();
        builder.addWithValueQueryParam("mobile", "=", mobile)
                .addWithValueQueryParam("verifyCode", "=", verifyCode)
                .addWithValueQueryParam("sendStatus", "=", smSendStatus.getValue());//状态为发送状态
        List<Sort> sortList = new ArrayList(1);
        sortList.add(new Sort("createTime", Sort.Direction.DESC));
        List<SmVerifyCode> smVerifyCodeList = smVerifyCodeDAO.query(builder.build(), sortList);
        if (null == smVerifyCodeList || smVerifyCodeList.size() < 0) {
            return null;
        }
        return smVerifyCodeList.get(0);
    }

    @Override
    @Transactional
    public MessageResultDto updateVerifyCode(SendVerifyCodeMsgReqDto sendDto, SendSmCommonRspDto rspDto) {
        SmVerifyCode smVerifyCode = new SmVerifyCode(sendDto);
        smVerifyCode.setMobile(sendDto.getMobile());
        smVerifyCode.setVerifyCode(sendDto.getVerifyCode());
        smVerifyCode.setSmChannelType(rspDto.getSmChannelType());
        smVerifyCode.setRetMsg(rspDto.getThirdText());
        smVerifyCode.setSendStatus(rspDto.getSmSendStatus());
        smVerifyCode.setModifyTime(new Date());

        Integer updatedRowCount=smVerifyCodeDAO.updateVerifyCode(smVerifyCode);
        if(updatedRowCount>0)
            return MessageResultDto.SUCCESS();
        String errMsg="SmVerifyCodeService updateVerifyCode fail";
        log.error(errMsg);
        return MessageResultDto.FAIL(errMsg);
    }

    @Override
    @Transactional
    public MessageResultDto saveVerifyCodeMsgReq(SendVerifyCodeMsgReqDto sendDto) {
        SmVerifyCode smVerifyCode = new SmVerifyCode(sendDto);
        smVerifyCode.setSendStatus(SmSendStatus.SENDING);
        smVerifyCode.setCreateTime(new Date());
        smVerifyCode.setModifyTime(new Date());
        this.insert(smVerifyCode);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public List<StatRecord> statSmVerifyCode(String startTime, String endTime) {
        Map<String, Object> map = new HashMap();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return smVerifyCodeDAO.statSmVerifyCode(map);
    }

    @Override
    public void batchDelete(List<SmVerifyCode> smVerifyCodeList) {
        smVerifyCodeDAO.batchDelete(smVerifyCodeList);
    }

}
