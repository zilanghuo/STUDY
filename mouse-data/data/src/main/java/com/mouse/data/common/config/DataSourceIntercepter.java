package com.mouse.data.common.config;

import org.aspectj.lang.JoinPoint;

/**
 * Created by lwf on 2017/4/12.
 */
public class DataSourceIntercepter {

    public void setdataSourceMysql(JoinPoint jp) {
        DataSourceHolder.setDataSources(DataSourceType.MYSQL);
    }

    public void setdataSourceOracle(JoinPoint jp) {
        DataSourceHolder.setDataSources(DataSourceType.ORACLE);
    }

}
