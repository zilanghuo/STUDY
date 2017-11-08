package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

/**
 * @author lwf
 * @date 2017/11/8
 * use: 星期聚合，若想要对比不同月份的星期，只能通过时间范围多次调用实现
 */
public class DayDateHistogram {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        //过滤时间:以月为单位，月的起始时间和结束时间，某一个字段
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(
                QueryBuilders.rangeQuery("requestTime")
                        .gte("2017-11-07T00:00:00.000+0000")
                        .lte("2017-11-07T23:59:59.000+0000"));

        //排序,根据字母顺序排序
        Terms.Order order = Terms.Order.term(true);

        SearchResponse sr = client.prepareSearch().setIndices("safe_local")
                .setTypes("customerVisitRecord")
                .setQuery(queryBuilder)
                .addAggregation(AggregationBuilders.terms("his").field("dayOfWeek").order(order))
                .execute().actionGet();

        Terms agg = sr.getAggregations().get("his");
        for (Terms.Bucket entry : agg.getBuckets()) {
            System.out.println(entry.getKey() + ":" + entry.getDocCount());
        }


    }
}
