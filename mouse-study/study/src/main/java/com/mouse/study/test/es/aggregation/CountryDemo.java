package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

/**
 * @author lwf
 * @date 2017/11/1
 * use: 分析城市的数量
 */
public class CountryDemo {

    public static void main(String[] args) throws Exception {

        TransportClient client = ConfigService.getClient();

        /**
         * 1、过滤时间点
         * 2、过滤非空数据
         * 3、排除城市数据
         */
        //过滤时间点,注意时间格式与ES相同
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(
                QueryBuilders.rangeQuery("startTimeOne")
                        .gte("2017-11-30 10:00:00.000"));

        //terms ：字段，需要修改字段属性为fielddata为true
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("agg").field("nameOne");

        SearchRequestBuilder builder = client.prepareSearch().setIndices("test01").setTypes("people2")
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder);


        System.out.println(builder.toString());
        SearchResponse sr = builder.execute().actionGet();
        Terms terms = sr.getAggregations().get("agg");
        for (Terms.Bucket entry : terms.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }


    }
}
