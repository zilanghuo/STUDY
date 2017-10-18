package com.mouse.study.test.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
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
                        .filter(QueryBuilders.matchPhrasePrefixQuery("errorMsg", "erro"))
                        .source("safe_dev")
                        .get();
        long deleted = response.getDeleted();
        System.out.println(deleted);
        System.out.println(response.getStatus());
    }

}
