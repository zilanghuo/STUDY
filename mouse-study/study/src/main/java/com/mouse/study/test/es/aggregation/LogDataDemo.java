package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author lwf
 * @date 2017/11/2
 * use:
 */
public class LogDataDemo {



    /**
     * 获取某个系统下的所有方法,并且统计各个方法的平均耗时时间,最大时间，等等
     *
     * @throws Exception
     */
    @org.junit.Test
    public void getMethod() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe_dev")
                .setTypes("LogDataRecord")
                .setQuery(QueryBuilders.termQuery("systemName", "交易系统"))
                .addAggregation(AggregationBuilders.terms("term").field("methodName")
                .subAggregation(AggregationBuilders.avg("avg").field("costTime"))
                        .subAggregation(AggregationBuilders.max("max").field("costTime"))
                )
                .addSort("costTime", SortOrder.DESC)
                .setSize(10)
                .execute().actionGet();


        System.out.println("总数量：" + sr.getHits().getTotalHits());
        Terms terms = sr.getAggregations().get("term");
        for (Terms.Bucket entry : terms.getBuckets()) {
            Avg avg = entry.getAggregations().get("avg");
            Max max = entry.getAggregations().get("max");
            System.out.println("methodName:"+entry.getKey() + ",count:" + entry.getDocCount()
                    +",avg:"+avg.getValue()+",max:"+max.getValue());
        }
    }

    /**
     * cost:排序
     *
     * @throws Exception
     */
    @org.junit.Test
    public void orderCostTime() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe_dev")
                .setTypes("LogDataRecord")
                .setQuery(QueryBuilders.termQuery("systemName", "交易系统"))
                .addAggregation(AggregationBuilders.avg("avg").field("costTime"))
                .addAggregation(AggregationBuilders.terms("term").field("methodName"))
                .addSort("costTime", SortOrder.DESC)
                .setSize(10)
                .execute().actionGet();


        System.out.println("总数量：" + sr.getHits().getTotalHits());
        Avg aggregation = sr.getAggregations().get("avg");
        System.out.println(aggregation.getValue());
        SearchHit[] hits = sr.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

    }
}
