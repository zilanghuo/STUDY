package com.zdmoney.data.common.config;

/**
 * Created by lwf on 2017/4/11.
 */
public class DataSourceHolder {
    private static ThreadLocal<String> dataSources = new ThreadLocal<String>();

    public static String getDataSources() {
        String source = dataSources.get();
        if (source == null) source = DataSourceType.MASTER;
        return source;
    }
    public static void setDataSources(String source) {
        dataSources.set(source);
    }
}
