package com.mouse.message.utils;

/**
 * Created by gaojc on 2016/11/10.
 */
public class SmConfig {
    private static SmConfig smConfig = null;

    private final SmUtils smUtils = (SmUtils) SpringContextHelper.getBean("smUtils");

    private SmConfig() {
    }

    private static SmConfig getInstance() {
        if (smConfig == null) {
            smConfig = new SmConfig();
        }
        return smConfig;
    }

    public static SmUtils getSmUtils() {
        return getInstance().smUtils;
    }

}
