package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessageResultDto;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/12.
 */
public interface ISmMarketMoveService {

    MessageResultDto moveSm(Date startTime, Date endTime);

    MessageResultDto moveSmVerifyCode(Date startTime, Date endTime);

    MessageResultDto moveSmNotify(Date startTime, Date endTime);
}
