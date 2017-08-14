package com.mouse.study.test.es.indecesAdmin;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
@Slf4j
public class AddApiDemo {

    public static void main(String[] args) throws Exception{
        testCreateIndex();
    }

    private static void testStatIndex() throws Exception {


    }

    private static void testCreateIndex() throws Exception{
        TransportClient client = ConfigService.getClient();
        CreateIndexRequest request = new CreateIndexRequest("test01");
        client.admin().indices().create(request);

    }

    private static void testOne() throws Exception {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("test", "test")
                .endObject();

        TransportClient client = ConfigService.getClient();
        PutMappingResponse response = client.admin().indices().preparePutMapping("twitter")
                .setType("user")
                .setSource("{\n" +
                        "  \"properties\": {\n" +
                        "    \"name\": {\n" +
                        "      \"type\": \"string\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .get();
        log.info(JackJsonUtil.objToStr(response));
    }
}
