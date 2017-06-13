package com.mouse.data.common.listener;

import com.mouse.vesta.client.VestaClient;
import com.mouse.vesta.service.VestaServer;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by lwf on 2017/6/5.
 */
@Slf4j
public class MotanListern implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("      *");
        log.info("    *    *");
        log.info("  *        *");
        log.info("*    motan    *");
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);

        VestaServer.initServer();
        log.info("vestaServer start succeed!");
        VestaClient.initClient();
        log.info("vestaClient start succeed!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
