package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;

/**
 * @author lwf
 * @date 2017/10/31
 * use:
 */
public class ExtendedStatsDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(AggregationBuilders.extendedStats("agg").field("costTime"))
                .execute().get();
        ExtendedStats agg = sr.getAggregations().get("agg");
        double min = agg.getMin();
        double max = agg.getMax();
        double avg = agg.getAvg();
        double sum = agg.getSum();
        long count = agg.getCount();
        double stdDeviation = agg.getStdDeviation();
        double sumOfSquares = agg.getSumOfSquares();
        double variance = agg.getVariance();
        System.out.println("min:" + min);
        System.out.println("max:" + max);
        System.out.println("avg:" + avg);
        System.out.println("sum:" + sum);
        System.out.println("count:" + count);
        System.out.println("stdDeviation:" + stdDeviation);
        System.out.println("sumOfSquares:" + sumOfSquares);
        System.out.println("variance:" + variance);

    }

}
