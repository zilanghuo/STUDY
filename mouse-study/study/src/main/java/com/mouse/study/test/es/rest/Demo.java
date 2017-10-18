package com.mouse.study.test.es.rest;

import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 * @author lwf
 * @date 2017/10/18
 * use:
 */
public class Demo {

    @org.junit.Test
    public void testOne() throws Exception{

        RestClient restClient = EsRestClient.getClient();
        Response response = restClient.performRequest("GET", "/");
        System.out.println(response.getStatusLine());
    }
}
