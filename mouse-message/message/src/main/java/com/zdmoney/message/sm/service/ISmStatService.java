package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.sm.model.SmChannel;

/**
 * Created by gaojc on 2016/11/10.
 */
public interface ISmStatService {

    MessageResultDto statSmChannelDay(String startTime, String endTime);

    MessageResultDto changeBstChannel();

}
