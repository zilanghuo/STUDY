package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
@Slf4j
public class SearchApiDemo {



    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
     /*   QueryBuilder qb = matchAllQuery();

        SearchResponse response = client.prepareSearch("dev112")
                .setTypes("dev")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(qb)
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] searchResponse = response.getHits().getHits();
        log.info("size:" + searchResponse.length);
        for (SearchHit hit : searchResponse) {
            System.out.println(JackJsonUtil.objToStr(hit.getSource()));
        }
*/
        testOne(client);
    }

    private static void testOne(TransportClient client) {
        SearchResponse response = client.prepareSearch("dmp")
                .setTypes("systemMapping")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("systemNo", "T002"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] searchResponse = response.getHits().getHits();
        log.info("size:" + searchResponse.length);
        for (SearchHit hit : searchResponse) {
            System.out.println(JackJsonUtil.objToStr(hit.getSource()));
        }
    }
}
