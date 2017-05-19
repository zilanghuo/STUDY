package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.pool.AbstractSmThread;
import com.zdmoney.message.sm.pool.Dh3tSmThread;

import java.util.concurrent.ExecutorService;

/**
 * Created by gaojc on 2017/1/6.
 */
public class AbstractDh3tSmService extends AbstractSmService {
    protected void dh3tSendMessage(SmChannelConfig config, SendSmCommonReqDto sendSmReqDto, SendSmType sendSmType,
                                   ExecutorService executor) {
        if (!checkTestSwitch()) {
            return;
        }
        AbstractSmThread bstThread = new Dh3tSmThread(sendSmReqDto, config, sendSmType);
        executor.submit(bstThread);
    }
}
