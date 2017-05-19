package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.dto.sm.SendSmCommonReqDto;
import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.sm.model.SmChannelConfig;
import com.zdmoney.message.sm.pool.AbstractSmThread;
import com.zdmoney.message.sm.pool.BlueSmThread;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/12/9.
 */
public class AbstractBlueSmService extends AbstractSmService {

    protected void blueSendMessage(SmChannelConfig config, SendSmCommonReqDto sendSmCommonReqDto, SendSmType sendSmType,
                                   ExecutorService executor) {
        if(!checkTestSwitch()) {
            return;
        }
        AbstractSmThread blueSmThread = new BlueSmThread(sendSmCommonReqDto, config, sendSmType);
        executor.submit(blueSmThread);
    }
}
