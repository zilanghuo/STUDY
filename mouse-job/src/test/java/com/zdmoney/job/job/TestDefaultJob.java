package com.zdmoney.job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;

/**
 * Created by rui on 16/8/29.
 */
public class TestDefaultJob extends DefaultJob {

    @Override
    public void process(ShardingContext shardingContext) {
        int shardingItem = shardingContext.getShardingItem();
        switch (shardingItem) {
            case 0:
                System.out.println("111111111111111");
                break;
            case 1:
                System.out.println("2222222222222222");
                break;
            case 2:
                System.out.println("3333333333333333");
                break;
        }

    }
}
