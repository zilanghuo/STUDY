package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;

/**
 * @author lwf
 * @date 2017/10/26
 * use:
 */
public class GlobalDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(AggregationBuilders.global("agg")
                        .subAggregation(AggregationBuilders.count("avgTwo").field("costTime")
                        )).execute().get();

        Global agg = sr.getAggregations().get("agg");
        long count = agg.getDocCount();
        System.out.println(count);
        ValueCount avgTwo = agg.getAggregations().get("avgTwo");
        System.out.println(avgTwo.getValue());

    }

}
