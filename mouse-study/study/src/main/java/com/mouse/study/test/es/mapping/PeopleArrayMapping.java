package com.mouse.study.test.es.mapping;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author lwf
 * @date 2018/1/23
 * use:
 */
public class PeopleArrayMapping {

    public static void main(String[] args) throws Exception {
        System.out.println(new PeopleArrayMapping().getMapping().toString());
        TransportClient client = ConfigService.getClient();
        //新建mapping
        PutMappingRequest request = Requests.putMappingRequest("bc_dev").type("peopleArray2").source(new PeopleArrayMapping().getMapping2());
        PutMappingResponse response = client.admin().indices().putMapping(request).actionGet();
        if (!response.isAcknowledged()) {
            System.out.println("创建失败");
        }
    }

    /**
     * {
     "query":{
     "terms":{
     "arrayName2":["array1"]
     }
     }
     }
     * @throws Exception
     */
    @org.junit.Test
    public void searchArray() throws Exception{
        TransportClient client = ConfigService.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("bc_dev").setTypes("peopleArray2")
                .setQuery(QueryBuilders.termQuery("arrayName2", "iii"));
        System.out.println(requestBuilder.toString());

        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(JackJsonUtil.objToStr(response.getHits().getHits()[0].getSource()));
    }

    @org.junit.Test
    public void sendData() throws Exception{
        List<String> list = new ArrayList();
        list.add("ooo");
        list.add("iii");
        list.add("ppp");
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("arrayName2", list)
                .endObject();
        TransportClient client = ConfigService.getClient();
        IndexResponse response = client.prepareIndex("bc_dev", "peopleArray2").setId(System.currentTimeMillis() + "")
                .setSource(builder).get();
        System.out.println(JackJsonUtil.objToStr(response));
    }

    /**
     * 索引格式
     *
     * @return
     */
    public XContentBuilder getMapping() {
        try {
            XContentBuilder mapping = jsonBuilder().prettyPrint()
                    .startObject()
                    .startObject("properties")
                    .startObject("arrayName")
                    .field("type","string")
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public XContentBuilder getMapping2() {
        try {
            XContentBuilder mapping = jsonBuilder().prettyPrint()
                    .startObject()
                    .startObject("properties")
                    .startObject("arrayName2")
                    .field("type","keyword")
                    .endObject()
                    .endObject()
                    .endObject();
            return mapping;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
