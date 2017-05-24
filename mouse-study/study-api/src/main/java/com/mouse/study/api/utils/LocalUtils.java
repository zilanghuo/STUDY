package com.mouse.study.api.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by rui on 15/11/20.
 */
@Slf4j
public class LocalUtils {

    public static String hostName() {
       /* try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            log.warn("" + e.getMessage());
        }*/
        return "未知IP";

    }
}
