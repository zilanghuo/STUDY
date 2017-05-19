package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.pool.AbstractSmThread;
import com.zdmoney.message.sm.pool.BstSmThread;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/12/9.
 */
public abstract class AbstractBstSmService extends AbstractSmService {

    protected void bstSendMessage(SmChannelConfig config, SendSmCommonReqDto sendSmReqDto, SendSmType sendSmType,
                                  ExecutorService executor) {
        if (!checkTestSwitch()) {
            return;
        }
        AbstractSmThread bstThread = new BstSmThread(sendSmReqDto, config, sendSmType);
        executor.submit(bstThread);
    }
}
