package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;

/**
 * Created by lwf on 2017/8/15.
 * use to do:
 */
@Slf4j
public class ProductAggDemo {

    public static void main(String[] args) throws Exception {
        testFour();
    }


    /**
     * 先对价格进行范围筛选，然后对颜色进行分组，最后求价格平均值
     *
     * @throws Exception
     */
    private static void testFour() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.range("rang_price").field("price")
                                .addUnboundedTo(200f)
                                .addRange(200f, 700f)
                                //  .addUnboundedFrom(700f)
                                .subAggregation(
                                        AggregationBuilders.terms("term_color_nest")
                                                .field("color").order(Terms.Order.count(true))
                                                .subAggregation(
                                                        AggregationBuilders.avg("avg_price").field("price")
                                                )
                                )
                )
                .setSize(100)
                .execute().actionGet();
        Range agg = sr.getAggregations().get("rang_price");
        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();             // Range as key
            Number from = (Number) entry.getFrom();          // Bucket from
            Number to = (Number) entry.getTo();              // Bucket to
            long docCount = entry.getDocCount();    // Doc count
            log.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
            Terms nestTerm = entry.getAggregations().get("term_color_nest");
            for (Terms.Bucket bucket : nestTerm.getBuckets()) {
                Avg avg = bucket.getAggregations().get("avg_price");
                log.info("分组,值：{}，个数：{}，平均值：{}", bucket.getKeyAsString(), bucket.getKeyAsNumber(), avg.getValue());
            }

        }

    }


    /**
     * 范围聚合，对价格返回筛选
     *
     * @throws Exception
     */
    private static void testThree() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.range("rang_price").field("price")
                                .addUnboundedTo(200f)
                                .addRange(200f, 700f)
                                .addUnboundedFrom(700f)
                )
                .setSize(100)
                .execute().actionGet();
        Range agg = sr.getAggregations().get("rang_price");
        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();             // Range as key
            Number from = (Number) entry.getFrom();          // Bucket from
            Number to = (Number) entry.getTo();              // Bucket to
            long docCount = entry.getDocCount();    // Doc count
            log.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
        }

    }

    /**
     * 先根据颜色分组，在统计对应的价格体系
     *
     * @throws Exception
     */
    private static void testTwo() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("by_color").field("color")
                                .size(100).order(Terms.Order.aggregation("ave_price", false))
                                .subAggregation(AggregationBuilders.avg("ave_price").field("price"))
                )
                .setSize(100)
                .execute().actionGet();
        Terms aggregation = sr.getAggregations().get("by_color");
        for (Terms.Bucket bucket : aggregation.getBuckets()) {
            log.info(bucket.getDocCount() + "---" + bucket.getKeyAsNumber());
            Avg avg = bucket.getAggregations().get("ave_price");
            log.info("avg:{}", avg.getValue());

        }

    }

    /**
     * 计算每个价格的商品数量
     *
     * @throws Exception
     */
    private static void testOne() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("by_color").field("color")
                                .size(100)
                )
                .setSize(100)
                .execute().actionGet();
        Terms aggregation = sr.getAggregations().get("by_color");
        for (Terms.Bucket bucket : aggregation.getBuckets()) {
            log.info(bucket.getDocCount() + "---" + bucket.getKeyAsNumber());
        }

    }


}
