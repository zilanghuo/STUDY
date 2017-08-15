package com.mouse.study.test.es.aggregation;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBounds;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;

/**
 * Created by lwf on 2017/8/10.
 * use to do: 聚合
 */
@Slf4j
public class AggregationSearchDemo {

    public static void main(String[] args) throws Exception {
        getMapping();
    }

    /**
     * top-hit索引
     *
     * @throws Exception
     */
    private static void testTopHit() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders
                                .terms("agg").field("age")
                                .subAggregation(
                                        AggregationBuilders.topHits("top")
                                                .explain(true)
                                                .size(100)
                                                .from(1)
                                ))
                .setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        log.info("size:【{}】", hits.getTotalHits());
        Terms agg = sr.getAggregations().get("agg");
        log.info("result:【{}】", JackJsonUtil.objToStr(agg));
        for (Terms.Bucket entry : agg.getBuckets()) {
            String key = (String) entry.getKey();
            long docCount = entry.getDocCount();
            log.info("key [{}], doc_count [{}]", key, docCount);
            TopHits topHits = entry.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                log.info(" -> id [{}], _source [{}]", hit.getId(), hit.getSourceAsString());
            }
        }

    }

    /**
     * 边界索引,字段类型必须为：geo-point
     *
     * @throws Exception
     */
    private static void testFilterThree() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders
                                .geoCentroid("age")
                                .field("age"))
                .setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        log.info("size:【{}】", hits.getTotalHits());
        GeoBounds agg = sr.getAggregations().get("agg");
        GeoPoint bottomRight = agg.bottomRight();
        GeoPoint topLeft = agg.topLeft();
        log.info("bottomRight {}, topLeft {}", bottomRight, topLeft);

    }


    private static void getMapping() throws Exception {
        TransportClient client = ConfigService.getClient();
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices().get("test02").getMappings();
        for (ObjectObjectCursor<String, MappingMetaData> cursor : mappings) {
            System.out.println(cursor.key); // 索引下的每个type
            System.out.println(cursor.value.getSourceAsMap()); // 每个type的mapping
        }

    }

    private static void testFilterTwo() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setTypes("peopleThree")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.dateHistogram("dh").field("startTimeOne"))
                .setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        InternalDateHistogram agg = sr.getAggregations().get("dh");
        for (Histogram.Bucket bucket : agg.getBuckets()) {
            log.info("--" + bucket.getKey() + "--" + bucket.getDocCount());
        }
        log.info("size:【{}】", hits.getTotalHits());
        for (Aggregation aggregation : sr.getAggregations().asList()) {
            log.info("aggregation:【{}】", aggregation.getName());
        }

    }


    private static void testFilter() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setTypes("people")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("by_count").field("age")
                        .subAggregation(AggregationBuilders.dateHistogram("by_year").field("startTime")
                                .dateHistogramInterval(DateHistogramInterval.MINUTE)))
                .setSize(100)
                .execute().actionGet();
        InternalTerms agg = (InternalTerms) sr.getAggregations().get("by_count");
        log.info("--" + JackJsonUtil.objToStr(agg));

        SearchHits hits = sr.getHits();
        log.info("size:【{}】", hits.getTotalHits());
        log.info("分片{}", sr.getTotalShards());
        for (Aggregation aggregation : sr.getAggregations().asList()) {
            log.info("aggregation:【{}】", aggregation.getName());
        }
        for (SearchHit searchHit : hits.getHits()) {
            log.info("result:【{}】", JackJsonUtil.objToStr(searchHit.getSource()));
        }

    }

    /**
     * 基础聚合
     *
     * @throws Exception
     */
    private static void avgAggDemo() throws Exception {
        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.percentiles("by_peopleId").field("age")
                ).setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        Percentiles agg = sr.getAggregations().get("by_peopleId");
        for (Percentile entry : agg) {
            double percent = entry.getPercent();    // Percent
            double value = entry.getValue();        // Value
            log.info("percent [{}], value [{}]", percent, value);
        }
        log.info("size:【{}】", hits.getTotalHits());
        log.info("size:【{}】", JackJsonUtil.objToStr(sr.getAggregations()));

    }
}
