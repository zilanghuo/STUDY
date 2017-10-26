package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.date.InternalDateRange;
import org.joda.time.DateTime;

/**
 * @author lwf
 * @date 2017/10/26
 * use: 时间范围
 */
@Slf4j
public class DateRange {

    public static void main(String[] args) throws Exception {

        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(AggregationBuilders.dateRange("dataRange")
                        .field("createTime")
                        .format("HH")
                        .addUnboundedTo("10")
                        .addRange("10", "20")
                        .addUnboundedFrom("20"))
                .execute().actionGet();

        InternalDateRange agg = sr.getAggregations().get("dataRange");
        for (InternalDateRange.Bucket bucket : agg.getBuckets()) {
            System.out.println("key:" + bucket.getKeyAsString() + ",count:" + bucket.getDocCount());
        }
        System.out.println("--------------------------------");

        Range dataRange = sr.getAggregations().get("dataRange");
        for (Range.Bucket entry : dataRange.getBuckets()) {
            String key = entry.getKeyAsString();                // Date range as key
            DateTime fromAsDate = (DateTime) entry.getFrom();   // Date bucket from as a Date
            DateTime toAsDate = (DateTime) entry.getTo();       // Date bucket to as a Date
            long docCount = entry.getDocCount();                // Doc count
            log.info("key [{}], from [{}], to [{}], doc_count [{}]", key, fromAsDate, toAsDate, docCount);
        }

    }

}
