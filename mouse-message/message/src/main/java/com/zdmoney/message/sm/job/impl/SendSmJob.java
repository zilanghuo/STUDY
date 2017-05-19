package com.zdmoney.message.sm.job.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.api.dto.sm.SendSmReqDto;
import com.zdmoney.message.api.dto.sm.SmSearchDto;
import com.zdmoney.message.api.utils.BeanUtil;
import com.zdmoney.message.sm.job.ISendSmJob;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.pool.SmThirdProviderThreadFactory;
import com.zdmoney.message.sm.provider.SmThirdProvider;
import com.zdmoney.message.sm.service.ISmMarketService;
import com.zdmoney.zdqd.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时发送短信job
 * Created by gaojc on 2016/11/21.
 */
@Slf4j
@Component
public class SendSmJob extends DefaultJob implements ISendSmJob, InitializingBean, DisposableBean {

    @Autowired
    private ISmMarketService smMarketService;
    @Autowired
    private SmThirdProvider smThirdProvider;

    private final static Integer PER_SEND_SIZE = 500;

    //发送短信线程池
    private ExecutorService executor;

    @Override
    public void process(ShardingContext shardingContext) {
        sendTimedShortMsg(new Date());
    }

    @Transactional
    private Integer updateStatus(Date date) {
        return smMarketService.batchUpdate(date);
    }

    /**
     * 发送定时的短信
     */
    public void sendTimedShortMsg(Date date) {
        Integer updateNum = updateStatus(date);
        if(updateNum <=0) {
            return;
        }
        int totalCount = smMarketService.getWaitSendSmsCount(date);//待发送短信短信
        if(totalCount <=0) {
            return;
        }
        int totalThreadSize = totalCount % PER_SEND_SIZE == 0 ? totalCount / PER_SEND_SIZE : totalCount / PER_SEND_SIZE + 1;
        SmSearchDto searchDto = new SmSearchDto();
        searchDto.setPageSize(PER_SEND_SIZE);
        for(int i=1; i<=totalThreadSize; i++) {
            searchDto.setPageNo(totalThreadSize-i+1);
            List<SmMarket> smMarketList = smMarketService.getWaitSendSms(date, searchDto);
            if(CollectionUtils.isNotEmpty(smMarketList)) {
                List<SendSmReqDto> sendSmReqDtoList1 = buildReq(smMarketList);
                for(SendSmReqDto sendSmReqDto : sendSmReqDtoList1) {
                    smThirdProvider.sendMarketMsg(sendSmReqDto);
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolExecutor(20, 100, 50L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),
                new SmThirdProviderThreadFactory("SendSmJob-pool-"));
    }

    private List<SendSmReqDto> buildReq(List<SmMarket> batchSmMarketList) {
        Map<String, List<SmMarket>> batchSmMarketMap =  BeanUtil.groupBeanList(batchSmMarketList, "batchNo");
        List<SendSmReqDto> sendSmReqDtoList = new ArrayList(PER_SEND_SIZE);
        for(Map.Entry<String, List<SmMarket>> entry : batchSmMarketMap.entrySet()) {
            List<SmMarket> subSmMarket = entry.getValue();
            List<String> mobiles = new ArrayList(subSmMarket.size());
            String sendMsg = "";
            for (SmMarket smMarket : subSmMarket) {
                if (StringUtils.isEmpty(sendMsg)) {
                    sendMsg = smMarket.getSendMsg();
                }
                mobiles.add(smMarket.getMobile());
            }
            SendSmReqDto reqDto = new SendSmReqDto();
            reqDto.setMobiles(mobiles);
            reqDto.setBatchNo(entry.getKey());
            reqDto.setSendMsg(sendMsg);
            sendSmReqDtoList.add(reqDto);
        }
        return sendSmReqDtoList;
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
    }
}
