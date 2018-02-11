package com.mouse.study.test.es.search;

import com.mouse.study.test.es.ConfigService;
import lombok.Data;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.ArrayList;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author lwf
 * @date 2018/1/30
 * use:
 */
public class RangeListDemo {

    @org.junit.Test
    public void aggRange() throws Exception{
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(QueryBuilders.rangeQuery("userId").lte(250));
        TransportClient client = ConfigService.getClient();
        SearchResponse response = client.prepareSearch("user_dev")
                .setTypes("peopleArray3")
                .setQuery(builder)
                .execute().actionGet();
        System.out.println(response.getHits().getTotalHits());

    }

    @org.junit.Test
    public void initData() throws Exception {
        TransportClient client = ConfigService.getClient();
        People people = new People();
        people.setId("1000002");
        ArrayList<Integer> prices = new ArrayList();
        prices.add(200);
        prices.add(300);
        people.setPrice(prices);
        IndexResponse response = client.prepareIndex("user_dev", "peopleArray3")
                .setId(people.getId())
                .setSource(people.gainBuilder()).execute().get();
        System.out.println(response.status());
    }

    @org.junit.Test
    public void initMapperAndIndex() throws Exception {
        TransportClient client = ConfigService.getClient();
        People people = new People();
        PutMappingRequest request = Requests.putMappingRequest("user_dev").type("peopleArray3").source(people.getMapping());
        PutMappingResponse response = client.admin().indices().putMapping(request).actionGet();
        System.out.println(response.isAcknowledged());
    }

    @Data
    class People {

        private String id;

        private ArrayList<Integer> price;


        public XContentBuilder gainBuilder() {
            XContentBuilder builder = null;
            try {
                builder = jsonBuilder().startObject();
                builder.field("id", this.id)
                        .field("userId", this.price)
                        .endObject();
            } catch (IOException e) {
            }
            return builder;
        }


        public XContentBuilder getMapping() {
            XContentBuilder mapping = null;
            try {
                mapping = jsonBuilder()
                        .startObject()
                        .startObject("properties")

                        .startObject("id")
                        .field("type", "keyword")
                        .endObject()

                        .startObject("price")
                        .field("type", "long")
                        .endObject()

                        //关联数据
                        .endObject()
                        .endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mapping;
        }
    }
}
