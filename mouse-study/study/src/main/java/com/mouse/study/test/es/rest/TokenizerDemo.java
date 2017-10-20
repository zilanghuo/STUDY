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
 * use:
 */
public class TokenizerDemo {

    RestClient restClient;

    Response response;

    public final String JSON_STRING = "{\"text\":\"这里 john.smith@global-international.com 是好记性the HELLO World不如烂笔头感叹号的博客园 terms whenever it encounters a character which is not\"}";

    Map<String, String> params = Collections.emptyMap();

    HttpEntity entity = new NStringEntity(JSON_STRING, ContentType.APPLICATION_JSON);

    @org.junit.Before
    public void before() throws Exception {
        restClient = EsRestClient.getLowClient();
    }

    @org.junit.Test
    public void testHierarchy() throws Exception {
        String json = "{\"text\":\"/one/two/three\"}";
        HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=path_hierarchy&pretty=true", params, entity);
    }

    @org.junit.Test
    public void CreateTokenizer() throws Exception {
        String jsonString = "{\n" +
                "  \"settings\": {\n" +
                "    \"analysis\": {\n" +
                "      \"analyzer\": {\n" +
                "        \"my_analyzer\": {\n" +
                "          \"tokenizer\": \"my_tokenizer\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"tokenizer\": {\n" +
                "        \"my_tokenizer\": {\n" +
                "          \"type\": \"pattern\",\n" +
                "          \"pattern\": \",\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        response = restClient.performRequest("PUT", "/dev_01", params, entity);

    }

    @org.junit.Test
    public void testPattern() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=pattern&pretty=true", params, entity);
    }

    @org.junit.Test
    public void testKeyword() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=keyword&pretty=true", params, entity);
    }

    @org.junit.Test
    public void testUaxUrlEmail() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=uax_url_email&pretty=true", params, entity);
    }

    @org.junit.Test
    public void testLowercase() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=lowercase&pretty=true", params, entity);
    }

    @org.junit.Test
    public void testLetter() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=letter&pretty=true", params, entity);
    }

    @org.junit.Test
    public void testStandard() throws Exception {
        response = restClient.performRequest("GET", "/dev_01/_analyze?tokenizer=standard&pretty=true", params, entity);
    }

    @org.junit.After
    public void after() throws Exception {
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
    }
}
