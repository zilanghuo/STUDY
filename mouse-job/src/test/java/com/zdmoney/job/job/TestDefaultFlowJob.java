package com.zdmoney.job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultFlowJob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rui on 16/8/30.
 */
public class TestDefaultFlowJob extends DefaultFlowJob<String> {

    @Override
    public List<String> fetch(ShardingContext shardingContext) {
        List<String> result = new LinkedList<>();
        int shardingItem = shardingContext.getShardingItem();
        switch (shardingItem) {
            case 0:
                result.add("1");
                break;
            case 1:
                result.add("2");
                break;
            case 2:
                result.add("3");
                break;
        }
        return result;
    }

    @Override
    public void process(ShardingContext shardingContext, List<String> data) {
        for (String d : data) {
            System.out.println("==========================" + d);
        }
    }
}
