package com.mouse.study.test.es.rest;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author lwf
 * @date 2017/10/18
 * use:
 */
public class HighDemo {

    /**
     * 查询
     */
    @org.junit.Test
    public void testOne() throws Exception {
        Thread.currentThread().setName("test-one");
        RestHighLevelClient client = EsRestClient.getHighClient();
        GetRequest request = new GetRequest(
                "bc_dev",
                "businessFeature",
                "S001_T1001");
        GetResponse response = client.get(request);
        System.out.println(response.getSourceAsString());
    }

}
