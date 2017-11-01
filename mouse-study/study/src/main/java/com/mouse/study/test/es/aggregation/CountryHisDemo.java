package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;

/**
 * @author lwf
 * @date 2017/10/31
 * use: 城市模型
 * 1、排除空的数据
 * 2、省份进行分析
 */
public class CountryHisDemo {

    public static void main(String[] args) throws Exception {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                // 排除非空限制
                .must(QueryBuilders.existsQuery("nameOne"));
        CardinalityAggregationBuilder aggregationBuilders = AggregationBuilders.cardinality("agg").field("nameOne");


        TransportClient client = ConfigService.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch().setIndices("safe222")
                .setTypes("people")
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilders);
        SearchResponse sr = requestBuilder
                .execute().get();

        System.out.println(requestBuilder.toString());
        System.out.println(sr.getHits().getTotalHits());
        SearchHit[] result = sr.getHits().getHits();
        for (SearchHit hit : result) {
            System.out.println(hit.getSource().get("country") + ":" + hit.getSource().get("city")+":"+hit.getSource().get("region"));
        }
        Cardinality aggregation = sr.getAggregations().get("agg");
        System.out.println(aggregation.getValue());


    }
}
