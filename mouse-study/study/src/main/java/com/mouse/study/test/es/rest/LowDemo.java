package com.mouse.study.test.es.rest;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 * @author lwf
 * @date 2017/10/18
 * use:
 */
public class LowDemo {

    @org.junit.Test
    public void testOne() throws Exception{

        RestClient restClient = EsRestClient.getLowClient();
        Response response = restClient.performRequest("GET", "/");
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
        System.out.println(statusCode);
        System.out.println(host.getHostName());
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
