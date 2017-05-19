package com.zdmoney.message.api.utils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SmHelperUtils {

    /**
     * 获取当前list的分组长度
     * @return
     */
    public static int groupThread(int collectSize, int perSize){
        int mod = collectSize % perSize;
        int listSize = mod == 0 ? collectSize / perSize : collectSize / perSize + 1;
        //当前线程数量大于最大值
        if(listSize >= Runtime.getRuntime().availableProcessors()) {
            //throw new Exception("sss");

        }
        return listSize;
    }
}
