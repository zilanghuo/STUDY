package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.dto.sm.SmChannelStatus;
import com.zdmoney.message.sm.pool.AbstractSmThread;
import com.zdmoney.message.utils.SmConfig;
import com.zdmoney.zdqd.util.StringUtils;

/**
 * Created by Administrator on 2016/11/4.
 */
public abstract class AbstractSmService {

    /**
     * 检查是否测试开关
     * @return
     */
    public boolean checkTestSwitch() {
        if(StringUtils.equals(SmChannelStatus.ON.name(), SmConfig.getSmUtils().getSmTestswitch())) {
            return true;
        }
        return false;
    }

}
