package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.joda.time.DateTimeZone;

/**
 * @author lwf
 * @date 2017/10/26
 * use: 时间柱形图
 */
public class DateHistogram {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        minHistogram(client);
    }

    /**
     * 根据分钟晒选数据
     * @param client
     */
    private static void minHistogram(TransportClient client) {

        //过滤时间点,注意时间格式与ES相同
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(
                QueryBuilders.rangeQuery("createTime")
                        .gte("2017-11-06T00:00:00.000+0000")
                        .lte("2017-11-06T12:00:00.000+0000"));
        System.out.println(queryBuilder.toString());
        //聚合
        //用于控制非0 的输出，固定此时间点
        ExtendedBounds extendedBounds = new ExtendedBounds("2017-11-06 00", "2017-11-06 12");

        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("dataTime")
                .field("createTime")
                .format("yyyy-MM-dd HH")
                .dateHistogramInterval(DateHistogramInterval.hours(1))
                .timeZone(DateTimeZone.forOffsetHours(8))
                .minDocCount(0)
                .extendedBounds(extendedBounds);

        System.out.println(aggregationBuilder.toString());
        SearchResponse sr = client.prepareSearch().setIndices("safe_dev")
                .setTypes("customerVisitRecord")
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .execute().actionGet();
        InternalDateHistogram agg = sr.getAggregations().get("dataTime");
        System.out.println("数量：" + agg.getBuckets().size());

        for (InternalDateHistogram.Bucket bucket : agg.getBuckets()) {
            System.out.println("key:" + bucket.getKeyAsString() + ",count:" + bucket.getDocCount());
        }
    }


    /**
     * 根据小时来分析数据
     *
     * @param client
     */
    private static void hourHistogram(TransportClient client) {

        //过滤时间点,注意时间格式与ES相同
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(
                QueryBuilders.rangeQuery("createTime")
                        .gte("2017-10-24T01:00:00.000+0000")
                        .lte("2017-10-25T23:00:00.000+0000"));

        //聚合
        //用于控制非0 的输出，固定此时间点
        ExtendedBounds extendedBounds = new ExtendedBounds("2017-10-24 01", "2017-10-25 23");
        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("dataTime")
                .field("createTime")
                .format("yyyy-MM-dd HH")
                .dateHistogramInterval(DateHistogramInterval.hours(1))
                .minDocCount(0)
                .extendedBounds(extendedBounds);

        System.out.println(aggregationBuilder.toString());
        SearchResponse sr = client.prepareSearch().setIndices("safe222")
                .setTypes("customerVisitRecord")
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .execute().actionGet();
        InternalDateHistogram agg = sr.getAggregations().get("dataTime");
        System.out.println("数量：" + agg.getBuckets().size());
        for (InternalDateHistogram.Bucket bucket : agg.getBuckets()) {
            System.out.println("key:" + bucket.getKeyAsString() + ",count:" + bucket.getDocCount());
        }
    }

    /**
     * 根据天来聚合数据
     *
     * @param client
     */
    private static void dateHistogram(TransportClient client) {
        ExtendedBounds extendedBounds = new ExtendedBounds("2017-10-01", "2017-10-31");
        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("dataTime")
                .field("createTime")
                .format("yyyy-MM-dd")
                .dateHistogramInterval(DateHistogramInterval.days(1))
                .minDocCount(0)
                .extendedBounds(extendedBounds)
                .offset("+6h");
        System.out.println(aggregationBuilder.toString());
        SearchResponse sr = client.prepareSearch().setIndices("safe222")
                .setTypes("customerVisitRecord")
                .addAggregation(aggregationBuilder)
                .execute().actionGet();
        InternalDateHistogram agg = sr.getAggregations().get("dataTime");
        System.out.println("数量：" + agg.getBuckets().size());
        for (InternalDateHistogram.Bucket bucket : agg.getBuckets()) {
            System.out.println("key:" + bucket.getKeyAsString() + ",count:" + bucket.getDocCount());
        }
    }
}
