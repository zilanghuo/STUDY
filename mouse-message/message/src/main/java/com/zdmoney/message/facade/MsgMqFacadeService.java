package com.zdmoney.message.facade;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.facade.IMsgMqFacadeService;
import com.zdmoney.message.message.service.IMsgMqService;
import com.zdmoney.mq.client.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/9/22.
 */
@Component
@Slf4j
public class MsgMqFacadeService implements IMsgMqFacadeService {
    @Autowired
    private IMsgMqService msgMqService;

    @Override
    public MessageResultDto<Boolean> reSend(String mqAddress, String mqGroup,
                                            String mqTopic, String mqTag, MqMessage sendMessage) {
        return msgMqService.add(mqAddress, mqGroup, mqTopic, mqTag, sendMessage);
    }
}
