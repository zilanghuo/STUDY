package com.mouse.study.test.log;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.Enumeration;
import java.util.Map;

/**
 * @author lwf
 * @date 2017/10/23
 * use:
 */
public class LogTypeProcessUnit {

    /**
     * 获取所使用的日志类型:logback\log4j\log4j2,以及使用到的log
     */
    private Map<String, Object> getLogBind() {
        String type = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
        Map<String, Object> loggerMap = new HashedMap(10);
        if (LogType.LOG4J.getValue().equals(type)) {
            Enumeration enumeration = org.apache.log4j.LogManager.getCurrentLoggers();
            while (enumeration.hasMoreElements()) {
                org.apache.log4j.Logger logger = (org.apache.log4j.Logger) enumeration.nextElement();
                if (logger.getLevel() != null) {
                    loggerMap.put(logger.getName(), logger);
                }
            }
            org.apache.log4j.Logger rootLogger = org.apache.log4j.LogManager.getRootLogger();
            loggerMap.put(rootLogger.getName(), rootLogger);
        } else if (LogType.LOGBACK.getValue().equals(type)) {
            ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
            for (ch.qos.logback.classic.Logger logger : loggerContext.getLoggerList()) {
                if (logger.getLevel() != null) {
                    loggerMap.put(logger.getName(), logger);
                }
            }
            ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("ROOT");
            loggerMap.put(rootLogger.getName(), rootLogger);
        } else if (LogType.LOG4J2.getValue().equals(type)) {
            org.apache.logging.log4j.core.LoggerContext loggerContext = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
            Map<String, org.apache.logging.log4j.core.config.LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
            for (org.apache.logging.log4j.core.config.LoggerConfig loggerConfig : map.values()) {
                String key = loggerConfig.getName();
                if (StringUtils.isBlank(key)) {
                    key = "root";
                }
                loggerMap.put(key, loggerConfig);
            }
        }
        return loggerMap;
    }

}
