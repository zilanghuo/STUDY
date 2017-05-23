package com.mouse.data.common.config;

/**
 * Created by lwf on 2017/4/11.
 */
public class DataSourceHolder {
    private static ThreadLocal<String> dataSources = new ThreadLocal();

    public static String getDataSources() {
        String source = dataSources.get();
        if (source == null) source = DataSourceType.MYSQL;
        return source;
    }
    public static void setDataSources(String source) {
        dataSources.set(source);
    }
}
