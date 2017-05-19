package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.*;
import com.zdmoney.message.sm.model.SmNotify;
import com.zdmoney.message.sm.model.StatRecord;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface ISmNotifyService extends EntityService<SmNotify> {
    /**
     * 查询短信
     *
     * @return
     */
    MessagePageResultDto<SmNotifyDto> searchSmNotify(SmSearchDto searchDto);

    MessageResultDto saveNotifyMsgReq(SendSmNotifyReqDto sendDto);

    SmNotify getRecentSmNotify(String mobile, String sendMsg, SmSendStatus smSendStatus);

    List<StatRecord> statSmNotify(String startTime, String endTime);

    void batchDelete(List<SmNotify> smNotifies);

    MessageResultDto updateNotifyMsg(SendSmNotifyReqDto sendSmNotifyReqDto, SendSmCommonRspDto sendSmCommonRspDto);
}
