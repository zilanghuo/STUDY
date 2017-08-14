package com.mouse.study.test.es.aggregation;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;

/**
 * Created by lwf on 2017/8/10.
 * use to do: 聚合
 */
@Slf4j
public class AggregationSearchDemo {

    public static void main(String[] args) throws Exception {
        getMapping();
    }


    private static void getMapping() throws Exception {
        TransportClient client = ConfigService.getClient();
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices().get("test01").getMappings();
        for (ObjectObjectCursor<String, MappingMetaData> cursor : mappings) {
            System.out.println(cursor.key); // 索引下的每个type
            System.out.println(cursor.value.getSourceAsMap()); // 每个type的mapping
        }

    }

    private static void testFilter() throws Exception{
        TransportClient client = ConfigService.getClient();



    }

    /**
     * text类型无法聚合
     *
     * @throws Exception
     */
    private static void avgAggDemo() throws Exception {
        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch()
                .setIndices("test01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.stats("by_peopleId").field("peopleId")
                ).setSize(100)
                .execute().actionGet();
        SearchHits hits = sr.getHits();
        log.info("size:【{}】", hits.getTotalHits());
        log.info("size:【{}】", JackJsonUtil.objToStr(sr.getAggregations()));
        // log.info(JackJsonUtil.objToStr(sr));


    }
}
