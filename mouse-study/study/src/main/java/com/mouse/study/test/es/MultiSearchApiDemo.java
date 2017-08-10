package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
@Slf4j
public class MultiSearchApiDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("update")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("user", "four")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

// You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            log.info("search:{}", JackJsonUtil.objToStr(response));
            nbHits += response.getHits().getTotalHits();
        }
    }
}
