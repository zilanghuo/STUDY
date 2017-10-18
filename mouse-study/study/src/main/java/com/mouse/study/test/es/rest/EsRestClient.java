package com.mouse.study.test.es.rest;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author lwf
 * @date 2017/10/18
 * use: rest 客户端
 */
public class EsRestClient {

    public static RestClient getClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("172.17.34.121", 9200, "http")).build();
        return restClient;
    }

}
