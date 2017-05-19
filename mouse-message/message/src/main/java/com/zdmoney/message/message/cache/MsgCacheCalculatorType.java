package com.zdmoney.message.message.cache;

/**
 * Created by Administrator on 2017/2/21.
 */
public enum MsgCacheCalculatorType {
    ADD{
        public Integer calNumber(Integer currentNum) {
            return currentNum + 1;
        }
    },//加
    REDUCE {
        public Integer calNumber(Integer currentNum) {
            if(currentNum == null || currentNum <= 0) {
                return 0;
            }
            return currentNum - 1;
        }
    };//减
    public abstract Integer calNumber(Integer currentNum);
}
