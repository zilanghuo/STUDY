package com.mouse.study.test.es.rest;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Serializable;

/**
 * @author lwf
 * @date 2017/10/18
 * use:
 */
public class EsRestClient implements Serializable{

    private static final long serialVersionUID = -814686107822570526L;

    public EsRestClient(){

    }

    public static RestHighLevelClient getHighClient() {
        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("172.17.34.121", 9200, "http")).build();
        RestHighLevelClient client =
                new RestHighLevelClient(lowLevelRestClient);
        return client;
    }

    public static RestClient getLowClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("172.17.34.121", 9200, "http")).build();
        return restClient;
    }

    @org.junit.Test
    public void test(){

    }

}
