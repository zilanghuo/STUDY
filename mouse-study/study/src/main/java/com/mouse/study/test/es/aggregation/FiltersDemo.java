package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;

import java.util.List;

/**
 * @author lwf
 * @date 2017/10/26
 * use: filters
 */
public class FiltersDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(
                        AggregationBuilders.filters("filter",
                                //是否相互影响
                                new FiltersAggregator.KeyedFilter("filterOne", QueryBuilders.matchQuery("accessChannel", "PC")),
                               // new FiltersAggregator.KeyedFilter("filterOne", QueryBuilders.termQuery("accessChannel", "PC")),
                                new FiltersAggregator.KeyedFilter("filterTwo", QueryBuilders.matchQuery("accessChannel", "ANDROID")))
                        /**
                         termQuery与matchQuery的区别
                         *1、termQuery:不分词，直接匹配。AccessChannel：由于分词，所有直接使用PC，会导致查询不到数据。
                         * 2、matchQuery:分词后再匹配
                         */

                )
                .execute().get();
        Filters filters = sr.getAggregations().get("filter");
        List<? extends Filters.Bucket> buckets = filters.getBuckets();
        for (Filters.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
        }

    }

}
