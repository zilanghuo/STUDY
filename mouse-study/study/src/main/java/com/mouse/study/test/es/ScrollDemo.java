package com.mouse.study.test.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author lwf
 * @date 2018/1/29
 * use: 获取全部数据或大量数据
 */
public class ScrollDemo {

    /**
     * 单次请求，获取所有数据，循环返回
     * @throws Exception
     */
    @org.junit.Test
    public void getScrollId() throws Exception {
        TransportClient esClient = ConfigService.getClient();
        SearchResponse scrollResp = esClient.prepareSearch("safe")
                .setTypes("customerVisitRecord")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .setScroll(new TimeValue(5000))
                .setSize(10000)
                .execute()
                .actionGet();
        int i = 0;
        do {
            ++i;
            System.out.println("i=" + i + ",size:" + scrollResp.getHits().getHits().length);
            scrollResp = esClient.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(5000)).execute().actionGet();
        } while (scrollResp.getHits().getHits().length != 0);
    }

}
