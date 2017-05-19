package com.zdmoney.message.message.cache;

import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
@NoArgsConstructor
public class CacheThread implements Runnable {

    private IMsgCacheCountService msgCacheCountService;
    private List<String> ids;
    private MsgCacheCalculatorType msgCalculatorType;

    public CacheThread (IMsgCacheCountService msgCacheCountService, List<String> ids, MsgCacheCalculatorType msgCalculatorType) {
        this.msgCacheCountService = msgCacheCountService;
        this.ids = ids;
        this.msgCalculatorType = msgCalculatorType;
    }

    @Override
    public void run() {
        if(MsgCacheCalculatorType.ADD == msgCalculatorType) {
            msgCacheCountService.batchAddCount(ids);
        } else if(MsgCacheCalculatorType.REDUCE == msgCalculatorType) {
            msgCacheCountService.batchReduceCount(ids, ids.size());
        }
        return;
    }
}
