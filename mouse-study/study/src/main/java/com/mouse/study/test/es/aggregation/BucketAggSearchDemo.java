package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;

/**
 * Created by lwf on 2017/8/15.
 * use to do:批量查询
 */
@Slf4j
public class BucketAggSearchDemo {

    public static void main(String[] args) throws Exception {
        testNestedAgg();
    }

    /**
     * 嵌套查询
     * @throws Exception
     */
    private static void testNestedAgg() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.nested("agg", "age"))
                .setSize(100)
                .execute().actionGet();
        Nested agg = sr.getAggregations().get("agg");
        log.info("size:{}",agg.getDocCount());
    }

    /**
     * miss聚合
     * @throws Exception
     */
    private static void testMissingAgg() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                //.setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.missing("agg").field("ageOne"))
                .setSize(100)
                .execute().actionGet();
        Missing agg = sr.getAggregations().get("agg");
        log.info("size:{}",agg.getDocCount());
    }

    /**
     * 多个filter 查询
     *
     * @throws Exception
     */
    private static void testFiltersAgg() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                //.setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders
                                .filters("agg",
                                        new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("ageOne", "16")),
                                        new FiltersAggregator.KeyedFilter("women", QueryBuilders.termQuery("ageOne", "17"))))
                .setSize(100)
                .execute().actionGet();
        Filters agg = sr.getAggregations().get("agg");
        for (Filters.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();            // bucket key
            long docCount = entry.getDocCount();            // Doc count
            log.info("key [{}], doc_count [{}]", key, docCount);
        }
    }

    /**
     * filter 查询
     *
     * @throws Exception
     */
    private static void testFilterAgg() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                //.setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders
                                .filter("agg", QueryBuilders.termQuery("nameOne", "hello5")))
                .setSize(100)
                .execute().actionGet();
        Filter agg = sr.getAggregations().get("agg");
        log.info("size:{}",agg.getDocCount());
        log.info("result:{}", JackJsonUtil.objToStr(agg.getMetaData()));
    }

}
