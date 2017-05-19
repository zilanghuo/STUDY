package com.zdmoney.data.common.hessian;

/**
 * Created by rui on 15/8/21.
 */
public final class ZDHessianReceive {

    private ZDHessianReceive() {
    }

    private static final ThreadLocal<String> RECEIVES = new ThreadLocal<String>();

    public static void put(String requestString){
        RECEIVES.set(requestString);
    }

    public static String get() {
        return RECEIVES.get();
    }


    public static void remove() {
        RECEIVES.remove();
    }

}
