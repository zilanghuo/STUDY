package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.api.common.dto.MessageOperator;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.ChannelDto;
import com.zdmoney.message.api.dto.sm.ChannelReqDto;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.api.dto.sm.SmSendStatus;
import com.zdmoney.message.api.utils.BeanUtil;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmStatDAO;
import com.zdmoney.message.sm.model.SmChannel;
import com.zdmoney.message.sm.model.SmStat;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.message.sm.service.*;
import com.zdmoney.message.utils.SmConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计短信发送情况
 * Created by gaojc on 2016/11/10.
 */
@Slf4j
@Service
public class SmStatService extends BaseServiceImpl<SmStat> implements ISmStatService {
    @Autowired
    private ISmChannelService smChannelService;
    @Autowired
    private ISmMarketService smMarketService;
    @Autowired
    private ISmVerifyCodeService smVerifyCodeService;
    @Autowired
    private ISmNotifyService smNotifyService;
    @Autowired
    private SmStatDAO smStatDAO;

    @Override
    @Transactional
    public MessageResultDto statSmChannelDay(String startTime, String endTime) {
        log.info("execute 统计短信发送情况 startTime{} endTime{} start", startTime, endTime);
        Date date = DateUtils.parse(startTime);
        SmStat smStat = new SmStat(DateUtils.format(date, "yyyyMM"), DateUtils.format(date, "dd"));
        List<SmStat> deleteSmStatList = smStatDAO.get(smStat);
        if (CollectionUtils.isNotEmpty(deleteSmStatList)) {
            this.delete(deleteSmStatList);
            log.info("execute 统计{} 短信发送情况删除{}条记录", date, deleteSmStatList.size());
        }

        List<StatRecord> statSmMarketRecords = smMarketService.statSmMarket(startTime, endTime);
        List<StatRecord> statSmVerifyCodeRecords = smVerifyCodeService.statSmVerifyCode(startTime, endTime);
        List<StatRecord> statSmNotifyRecords = smNotifyService.statSmNotify(startTime, endTime);
        List<StatRecord> statRecordList = new ArrayList<>();
        statRecordList.addAll(statSmMarketRecords);
        statRecordList.addAll(statSmVerifyCodeRecords);
        statRecordList.addAll(statSmNotifyRecords);

        Map<String, List<StatRecord>> smChannelTypeMap = BeanUtil.groupBeanList(statRecordList, "smChannelType");
        for (Map.Entry<String, List<StatRecord>> entry : smChannelTypeMap.entrySet()) {
            SmChannelType smChannelType = SmChannelType.getSmChannelTypeByValue(entry.getKey());
            if (smChannelType == null) {
                log.info("当前统计短信发送情况异常{}", date);
                continue;
            }
            log.info("当前统计渠道{} 统计{} 短信发送情况", smChannelType.getName(), date);
            smStat.setSmChannelType(smChannelType);
            Map<String, List<StatRecord>> smSendStatusMap = BeanUtil.groupBeanList(entry.getValue(), "sendStatus");
            List<StatRecord> statRecordList1 = smSendStatusMap.get(SmSendStatus.SUCCESS.getValue()+"");//成功
            List<StatRecord> statRecordList2 = smSendStatusMap.get(SmSendStatus.FAIL.getValue()+"");//失败
            int successNum = 0;
            int failNum = 0;
            if (CollectionUtils.isNotEmpty(statRecordList1)) {
                for (StatRecord statRecord : statRecordList1) {
                    successNum += statRecord.getSendNum();
                }
            }
            if (CollectionUtils.isNotEmpty(statRecordList2)) {
                for (StatRecord statRecord : statRecordList2) {
                    failNum += statRecord.getSendNum();
                }
            }
            log.info("当前统计渠道{} 统计{} 短信发送情况, 成功{} 失败{} 总数量{} ", smChannelType.getName(), date, successNum, failNum, successNum + failNum);
            smStat.setSendSucceedNum(successNum);
            smStat.setSendFailedNum(failNum);
            smStat.setSendNum(successNum + failNum);
            smStat.setCreateTime(new Date());
            smStat.setModifyTime(new Date());
            smStat.setOperator(MessageOperator.SYS.name());
            smStatDAO.insert(smStat);

            //当月发送数量累计
            SmChannel smChannel = smChannelService.getSmChannelByNo(smStat.getSmChannelType().getNo());
            log.info("当前统计渠道{} 统计{} 短信前短信数量{} ",smChannelType.getName(),date, smChannel.getMonthNumber());
            int monthNumber = smChannel.getMonthNumber() + successNum + failNum;
            log.info("当前统计渠道{} 统计{} 短信后短信数量{} ",smChannelType.getName(),date, monthNumber);
            smChannel.setMonthNumber(monthNumber);
            smChannel.setModifyTime(new Date());
            smChannelService.update(smChannel);
            //更新渠道
        }
        log.info("execute 统计短信发送情况 startTime{} endTime{} end", startTime, endTime);
        return MessageResultDto.SUCCESS();
    }

    //253短信数量发送大于n万（每月最大可发送数量）的时候，停用232并且启用博士通
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public MessageResultDto changeBstChannel() {
        MessageResultDto<ChannelDto> channel = smChannelService.searchStartChannel();

        int monthNumber = channel.getData().getMonthNumber();
        SmChannelType channelType = channel.getData().getSmChannelType();
        if(SmChannelType.BLUE.equals(channelType) && monthNumber >=SmConfig.getSmUtils().getResetMainChannelNumber()){
            smChannelService.start(new ChannelReqDto(SmChannelType.BST.getNo()));
        }else if(SmChannelType.BST.equals(channelType) && monthNumber >=SmConfig.getSmUtils().getResetOtherChannelNumber()){
            smChannelService.start(new ChannelReqDto(SmChannelType.DH3T.getNo()));
        }else if(SmChannelType.DH3T.equals(channelType) && monthNumber >=SmConfig.getSmUtils().getResetOtherChannelNumber()){
            smChannelService.start(new ChannelReqDto(SmChannelType.BLUE.getNo()));
        }
        return MessageResultDto.SUCCESS();
    }

}
