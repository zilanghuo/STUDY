package com.mouse.study.common.interceptor.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by lwf on 2017/5/25.
 */
@Slf4j
@Intercepts(value = {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class LanguageInterceptor implements Interceptor {

    private static ThreadLocal<String> local = new ThreadLocal();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("enter interceptor !");
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String executeSql = statementHandler.getBoundSql().getSql();
        log.info("sql:", executeSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
