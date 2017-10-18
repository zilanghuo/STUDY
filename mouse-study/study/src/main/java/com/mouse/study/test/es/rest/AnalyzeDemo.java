package com.mouse.study.test.es.rest;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.util.Collections;
import java.util.Map;

/**
 * @author lwf
 * @date 2017/10/18
 * use: 分析器
 */
public class AnalyzeDemo {

    @org.junit.Test
    public void testOne() throws Exception{
        RestClient restClient = EsRestClient.getLowClient();
        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{\n" +
                "  \"settings\": {\n" +
                "     \"refresh_interval\": \"5s\",\n" +
                "     \"number_of_shards\" :   1, // 一个主节点\n" +
                "     \"number_of_replicas\" : 0 // 0个副本，后面可以加\n" +
                "  },\n" +
                "  \"mappings\": {\n" +
                "    \"_default_\":{\n" +
                "      \"_all\": { \"enabled\":  false } // 关闭_all字段，因为我们只搜索title字段\n" +
                "    },\n" +
                "    \"resource\": {\n" +
                "      \"dynamic\": false, // 关闭“动态修改索引”\n" +
                "      \"properties\": {\n" +
                "        \"title\": {\n" +
                "          \"type\": \"string\",\n" +
                "          \"index\": \"analyzed\",\n" +
                "          \"fields\": {\n" +
                "            \"cn\": {\n" +
                "              \"type\": \"string\",\n" +
                "              \"analyzer\": \"ik_max_word\"\n" +
                "            },\n" +
                "            \"en\": {\n" +
                "              \"type\": \"string\",\n" +
                "              \"analyzer\": \"english\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", "/dev_02", params, entity);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
    }

    /**
     * 测试默认的分词器：这里是好记性不如烂笔头感叹号的博客园
     * @throws Exception
     */
    @org.junit.Test
    public void testTwo() throws Exception{
        RestClient restClient = EsRestClient.getLowClient();
        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{\"text\":\"这里是好记性不如烂笔头感叹号的博客园\"}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("GET", "/dev_01/_analyze", params, entity);
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
    }
}