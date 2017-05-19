package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.zdqd.service.EntityService;

import java.util.Date;
import java.util.List;

/**
 * 查询短信
 * Created by gaojc on 2016/11/4.
 */
public interface ISmMarketService extends EntityService<SmMarket> {

    /**
     * 查询短信
     *
     * @return
     */
    MessagePageResultDto<ShortMsgDto> searchShortMsg(SmSearchDto searchDto);

    /**
     * 保存发送短信记录
     *
     * @param sendSmReqDto
     * @param type
     * @return
     */
    MessageResultDto saveSmReq(SendSmReqDto sendSmReqDto, SendSmType type);

    List<SmMarket> getListBySmReqDto(SendSmReqDto sendSmReqDto);

    List<SmMarket> getWaitSendSms(Date date, SmSearchDto searchDto);

    /**
     * 待发送中条数
     * @param date
     * @return
     */
    int getWaitSendSmsCount(Date date);

    Integer batchUpdate(Date date);

    List<StatRecord> statSmMarket(String startTime, String endTime);

    Integer batchUpdateRetMsg(NotifyUpdateMqSmDto notifyUpdateMqSmDto);

    Integer batchDelete(List<SmMarket> smMarkets);
}
