package com.zdmoney.message.log.service.impl;

import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.log.dao.LogControlConfigDAO;
import com.zdmoney.message.log.model.LogControlConfig;
import com.zdmoney.message.log.service.ILogControlConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/4.
 */
@Slf4j
@Service
public class LogControlConfigService extends BaseServiceImpl<LogControlConfig> implements ILogControlConfigService, InitializingBean {

    @Autowired
    private LogControlConfigDAO logCtrlConfigDAO;

    //数据库也用这个值，用来控制是否入库
    private static String DATA_CONFIG = "log.access.control.key";

    @Override
    @Transactional
    @CacheEvict(key="dataConfig", beforeInvocation = true)
    public MessageResultDto on() {
        LogControlConfig config = getOne();
        config.setIsLog(true);
        config.setModifyTime(new Date());
        logCtrlConfigDAO.update(config);
        return MessageResultDto.SUCCESS();
    }

    @Override
    @Transactional
    @CacheEvict(key="dataConfig", beforeInvocation = true)
    public MessageResultDto off() {
        LogControlConfig config = getOne();
        config.setIsLog(false);
        config.setModifyTime(new Date());
        logCtrlConfigDAO.update(config);
        return MessageResultDto.SUCCESS();
    }

    @Override
    public boolean checkSwitch() {
        LogControlConfig config = getOne();
        if (config == null) {
            log.error("logDataConfig is null");
            return false;
        }
        return config.getIsLog();
    }

    @Override
    @Cacheable(key="dataConfig")
    public LogControlConfig getOne() {
        LogControlConfig config = new LogControlConfig(DATA_CONFIG);
        return logCtrlConfigDAO.getOne(config);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
