package com.mouse.study.test.es.search;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by lwf on 2017/8/10.
 * use to do:单个查询
 */
@Slf4j
public class SimpleSearch {

    public static void main(String[] args) throws Exception {
        multiSearch();
    }


    /**
     * 聚合查询，对结果进行分析
     *
     * @throws Exception
     */
    private static void aggSearch() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch()
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(
                        AggregationBuilders.terms("user").field("field")
                )
                /*.addAggregation(
                        AggregationBuilders.dateHistogram("agg2")
                                .field("birth")
                                .dateHistogramInterval(DateHistogramInterval.YEAR)
                )*/
                .get();

        Terms agg1 = sr.getAggregations().get("user");
        log.info("----"+JackJsonUtil.objToStr(agg1));
    }

    /**
     * 组合查询，返回对应各自条件的集合
     *
     * @throws Exception
     */
    private static void multiSearch() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("update")).setSize(10);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("user", "system")).setSize(10);
        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            log.info("index:" + JackJsonUtil.objToStr(item.getResponse().getHits()));
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
    }

    /**
     * 滚动输出，分批输出数据集。无需传分页属性。可用于报表打印
     *
     * @throws Exception
     */
    private static void scrollSearch() throws Exception {
        TransportClient client = ConfigService.getClient();
        QueryBuilder qb = termQuery("user", "system");

        SearchResponse scrollResp = client.prepareSearch()
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(4000))
                .setQuery(qb)
                .setSize(10).get(); //max of 100 hits will be returned for each scroll
        do {
            log.info("滚动输出，大小：" + scrollResp.getHits().getHits().length);
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                log.info("hit result:{}", JackJsonUtil.objToStr(hit.getSource()));
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(2000)).execute().actionGet();
        }
        while (scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.


    }


    /**
     * 查询,支持所有的字段查询(如何进行编码转换)
     *
     * @throws Exception
     */
    private static void testThree() throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse response = client.prepareSearch("twitter") //索引条件，可以为空，多个索引值
                .setTypes("dev") //type:可以为空
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery("user", "system")) // 字段属性
                .setQuery(termQuery("productNo", "0003")) // 字段属性
                .setPostFilter(QueryBuilders.matchAllQuery())     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .get();

        log.info("size:" + response.getHits().getHits().length);

    }

    /**
     * 多字段条件获取值,可以多个index,可以列表
     *
     * @throws Exception
     */
    private static void testTwo() throws Exception {
        TransportClient client = ConfigService.getClient();
        MultiGetRequest.Item item = new MultiGetRequest.Item("twitter", "dev", "AV3K2l4qHEzmNHcfnpT4");
        MultiGetRequest.Item item2 = new MultiGetRequest.Item("twitter", "dev", "AV3K185sZWCH5NmYlq1V");
        MultiGetRequest.Item item3 = new MultiGetRequest.Item("dev112", "dev", "29");

        MultiGetResponse response = client.prepareMultiGet()
                .add(item)
                .add(item2)
                .add(item3)
                .get();
        MultiGetItemResponse[] responses = response.getResponses();
        log.info("size:" + responses.length);
        for (MultiGetItemResponse itemResponse : responses) {
            log.info(itemResponse.getIndex() + "----" + itemResponse.getType());
            log.info(JackJsonUtil.objToStr(itemResponse.getResponse()));
            log.info(itemResponse.getResponse().getSourceAsString());
        }

    }


    /**
     * 通过id获取单个记录
     *
     * @throws Exception
     */
    private static void testOne() throws Exception {
        TransportClient client = ConfigService.getClient();
        GetResponse response = client.prepareGet("twitter", "dev", "AV3K2l4qHEzmNHcfnpT4").get();
        log.info("response【{}】", JackJsonUtil.objToStr(response));
        log.info("result【{}】", JackJsonUtil.objToStr(response.getSource()));
    }

}
