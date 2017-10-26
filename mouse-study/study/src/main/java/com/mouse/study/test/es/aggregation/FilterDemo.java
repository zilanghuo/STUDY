package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;

import java.util.List;

/**
 * @author lwf
 * @date 2017/10/26
 * use: filter
 */
public class FilterDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(
                        AggregationBuilders.filter("filterOne", QueryBuilders.matchQuery("accessChannel", "ANDROID"))
                                .subAggregation(AggregationBuilders.avg("avgOne").field("costTime"))
                )
                .execute().get();
        List<Aggregation> aggregations = sr.getAggregations().asList();
        for (Aggregation aggregation : aggregations) {
            System.out.println("--" + aggregation.getName());
        }

        Filter filterOne = sr.getAggregations().get("filterOne");
        System.out.println("filter size:" + filterOne.getDocCount());
        /**
         * 求平均值
         */
        Avg avgOne = filterOne.getAggregations().get("avgOne");
        System.out.println("avgOne size:" + avgOne.getValue());
    }

}
