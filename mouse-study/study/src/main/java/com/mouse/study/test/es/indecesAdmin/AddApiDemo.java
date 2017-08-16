package com.mouse.study.test.es.indecesAdmin;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
@Slf4j
public class AddApiDemo {

    public static void main(String[] args) throws Exception {
        testDeleteIndex();
    }


    /**
     * 获取所有的索引
     *
     * @throws Exception
     */
    private static void testGetIndex() throws Exception {
        TransportClient client = ConfigService.getClient();
        GetIndexRequest request = new GetIndexRequest();
        ActionFuture<GetIndexResponse> index = client.admin().indices().getIndex(request);
        GetIndexResponse response = index.actionGet();
        log.info("{}", JackJsonUtil.objToStr(index.actionGet().getIndices()));
    }

    /**
     * 删除索引
     *
     * @throws Exception
     */
    private static void testDeleteIndex() throws Exception {
        TransportClient client = ConfigService.getClient();
        DeleteIndexRequest deleteIndex = new DeleteIndexRequest("test04");
        DeleteIndexResponse response = client.admin().indices().delete(deleteIndex).actionGet();
        log.info("response:{}", JackJsonUtil.objToStr(response));

    }


    /**
     * 创建索引
     *
     * @throws Exception
     */
    private static void testCreateIndex() throws Exception {
        TransportClient client = ConfigService.getClient();
        Map<String, String> maps = new HashMap();
        maps.put("number_of_shards", "5"); //5个分片
        maps.put("number_of_replicas", "3"); //2个备份
        CreateIndexRequest request = new CreateIndexRequest("test04")
                .settings(maps);
        CreateIndexResponse response = client.admin().indices().create(request).actionGet();
        log.info("response:{}", JackJsonUtil.objToStr(response));

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
