package com.mouse.study.test.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lwf
 * @date 2017/10/23
 * use: 日志类型
 */
@Getter
@AllArgsConstructor
public enum LogType {
    LOG4J("org.slf4j.impl.Log4jLoggerFactory"),
    LOGBACK("ch.qos.logback.classic.util.ContextSelectorStaticBinder"),
    LOG4J2("org.apache.logging.slf4j.Log4jLoggerFactory");

    private String value;
}
