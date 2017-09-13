package com.mouse.study.test.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
public class GetApiDemo {

    private static final Logger logger = LoggerFactory.getLogger(GetApiDemo.class);


    public static void main(String[] args) throws Exception {
       /* TransportClient client = ConfigService.getClient();
        GetResponse response = client.prepareGet("test03", "geo01", "AV3o6ge0DeQIl-pxDb6j").get();
        logger.info(response.getId());
        logger.info("-----" + response.getSource());
        logger.info(response.getId());

        System.out.println(JackJsonUtil.objToStr(response));*/

        testFuzz();
    }

    public static void testFuzz() throws Exception {
        TransportClient client = ConfigService.getClient();
       /* SearchRequestBuilder requestBuilder = client.prepareSearch("dmp").setTypes("businessReportMapping")
                .setQuery(QueryBuilders.queryStringQuery("条数据").allowLeadingWildcard(true)
                        .autoGeneratePhraseQueries(true)
                        systemMapping
                        businessReportMapping
                        businessFeatureMapping
                );*/


        SearchRequestBuilder requestBuilder = client.prepareSearch("dmp").setTypes("businessReportMapping")
                .setQuery(QueryBuilders.queryStringQuery("\"批量数据273\""));

        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder
                .setFrom(0)
                .addSort("createTime", SortOrder.DESC)
                .setSize(300)
                .execute().actionGet();
        SearchHit[] searchHits = response.getHits().getHits();
        System.out.println("总大小:" + response.getHits().getTotalHits());
        System.out.println("大小:" + searchHits.length);
        for (SearchHit hit : searchHits) {
            System.out.println("hit:" + hit.getSourceAsString());
        }
    }
}
