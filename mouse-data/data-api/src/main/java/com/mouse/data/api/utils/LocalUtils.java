package com.mouse.data.api.utils;

/**
 * Created by rui on 15/11/20.
 */
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
