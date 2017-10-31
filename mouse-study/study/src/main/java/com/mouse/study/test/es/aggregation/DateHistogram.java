package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;

/**
 * @author lwf
 * @date 2017/10/26
 * use: 时间柱形图
 */
public class DateHistogram {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        ExtendedBounds extendedBounds = new ExtendedBounds("2017-10-01","2017-10-31");

        DateHistogramAggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram("dataTime")
                .field("createTime")
                .format("yyyy-MM-dd")
                .dateHistogramInterval(DateHistogramInterval.days(2))
                .minDocCount(0)
                .extendedBounds(extendedBounds)
                .offset("+6h");
        System.out.println(aggregationBuilder.toString());
        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(aggregationBuilder)
                .setSize(12)
                .execute().actionGet();
        InternalDateHistogram agg = sr.getAggregations().get("dataTime");
        System.out.println("数量：" + agg.getBuckets().size());
        for (InternalDateHistogram.Bucket bucket : agg.getBuckets()) {
            System.out.println("key:" + bucket.getKeyAsString() + ",count:" + bucket.getDocCount());
        }


    }
}
