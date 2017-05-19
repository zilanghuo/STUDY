package com.zdmoney.message.log.service;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.log.model.LogControlConfig;
import com.zdmoney.zdqd.service.EntityService;

/**
 * Created by Administrator on 2017/2/4.
 */
public interface ILogControlConfigService extends EntityService<LogControlConfig> {

    MessageResultDto on();

    MessageResultDto off();

    boolean checkSwitch();

    LogControlConfig getOne();
}
