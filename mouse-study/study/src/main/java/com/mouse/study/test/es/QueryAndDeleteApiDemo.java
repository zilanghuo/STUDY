package com.mouse.study.test.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

/**
 * Created by lwf on 2017/8/9.
 * use to do:查询删除
 */
@Slf4j
public class QueryAndDeleteApiDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchPhrasePrefixQuery("notifyContent", "测试体012345"))
                        .source("dmp")
                        .get();
        long deleted = response.getDeleted();
        System.out.println(deleted);
    }
}
