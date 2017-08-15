package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.ProductTest02;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.joda.time.DateTime;

/**
 * Created by lwf on 2017/8/15.
 * use to do:
 */
@Slf4j
public class ProductAggDemo {

    public static void main(String[] args) throws Exception {
        testFive();
    }


    /**
     * 时间范围排序
     *
     * @throws Exception
     */
    private static void testFive() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.dateRange("rang_price").field("createTime")
                                .format("yyyy")
                                .addUnboundedTo("2016")
                                .addRange("2017", "2018")
                                .addUnboundedFrom("2018")
                )
                .setSize(100)
                .execute().actionGet();
        Range agg = sr.getAggregations().get("rang_price");
        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();                // Date range as key
            DateTime fromAsDate = (DateTime) entry.getFrom();   // Date bucket from as a Date
            DateTime toAsDate = (DateTime) entry.getTo();       // Date bucket to as a Date
            long docCount = entry.getDocCount();                // Doc count
            log.info("key [{}], from [{}], to [{}], doc_count [{}]", key, fromAsDate, toAsDate, docCount);
        }

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

    /**
     * 查询数据
     *
     * @throws Exception
     */
    private static void testSearch() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setIndices("test02")
                .setTypes("product")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .setSize(5)
                .execute().actionGet();
        SearchHit[] hits = sr.getHits().getHits();
        for (SearchHit searchHit : hits) {
            log.info(JackJsonUtil.objToStr(searchHit.getSource()));
            ProductTest02 tset = (ProductTest02)JackJsonUtil.strToObj(searchHit.getSourceAsString(), ProductTest02.class);
            log.info(tset.getCreateTime());
        }
    }

}
