package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;

/**
 * @author lwf
 * @date 2017/10/26
 * use: 时间柱形图
 */
public class DateHistogram {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(AggregationBuilders.dateHistogram("dataTime")
                        .field("createTime").interval(2).format("yyyy-MM-dd HH时")
                        .dateHistogramInterval(DateHistogramInterval.HOUR))
                .setSize(100)
                .execute().actionGet();
        InternalDateHistogram agg = sr.getAggregations().get("dataTime");
        for (InternalDateHistogram.Bucket bucket:agg.getBuckets()){
            System.out.println("key:"+bucket.getKeyAsString()+",count:"+bucket.getDocCount());
        }

    }
}
