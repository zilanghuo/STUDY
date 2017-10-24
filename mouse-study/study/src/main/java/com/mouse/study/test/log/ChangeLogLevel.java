package com.mouse.study.test.log;

import ch.qos.logback.classic.Logger;
import org.apache.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.LoggerFactory;

/**
 * @author lwf
 * @date 2017/10/23
 * use: 修改日志级别
 */
public class ChangeLogLevel {

    private void setLogLevel(String logType, String className, String level) {

        if (LogType.LOG4J.getValue().equals(logType)) {
            org.apache.log4j.Logger logger = org.apache.log4j.LogManager.getRootLogger();
            logger.setLevel(Level.toLevel("level"));
        }else if (LogType.LOGBACK.getValue().equals(logType)){
            ch.qos.logback.classic.LoggerContext loggerContext =  (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
            Logger logger = loggerContext.getLogger("ROOT");
            logger.setLevel(ch.qos.logback.classic.Level.toLevel(level));
        }else if (LogType.LOG4J2.getValue().equals(logType)){
            Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(level));
        }


    }
       /* for (LoggerBean loggerbean : loggerList) {
            Object logger = loggerMap.get(loggerbean.getName());
            if (logger == null) {
                throw new RuntimeException("需要修改日志级别的Logger不存在");
            }
            if (logFrameworkType == LogFrameworkType.LOG4J) {
                org.apache.log4j.Logger targetLogger = (org.apache.log4j.Logger) logger;
                org.apache.log4j.Level targetLevel = org.apache.log4j.Level.toLevel(loggerbean.getLevel());
                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOGBACK) {
                ch.qos.logback.classic.Logger targetLogger = (ch.qos.logback.classic.Logger) logger;
                ch.qos.logback.classic.Level targetLevel = ch.qos.logback.classic.Level.toLevel(loggerbean.getLevel());
                targetLogger.setLevel(targetLevel);
            } else if (logFrameworkType == LogFrameworkType.LOG4J2) {
                org.apache.logging.log4j.core.config.LoggerConfig loggerConfig = (org.apache.logging.log4j.core.config.LoggerConfig) logger;
                org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.toLevel(loggerbean.getLevel());
                loggerConfig.setLevel(targetLevel);
                org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
                ctx.updateLoggers(); // This causes all Loggers to refetch information from their LoggerConfig.
            } else {
                throw new RuntimeException("Logger的类型未知,无法处理!");
            }
        }*/
}
