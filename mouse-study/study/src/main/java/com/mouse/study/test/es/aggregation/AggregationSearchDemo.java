package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;

/**
 * Created by lwf on 2017/8/10.
 * use to do: 聚合，暂停
 */
@Slf4j
public class AggregationSearchDemo {

    public static void main(String[] args) throws Exception {
        avgAggDemo();
    }

    /**
     *
     * @throws Exception
     */
    private static void avgAggDemo() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("twitter")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("by_productNo").field("0001")
                ).setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        log.info("size:【{}】", hits.getTotalHits());
        log.info("size:【{}】", JackJsonUtil.objToStr(sr.getAggregations()));
       // log.info(JackJsonUtil.objToStr(sr));


    }
}
