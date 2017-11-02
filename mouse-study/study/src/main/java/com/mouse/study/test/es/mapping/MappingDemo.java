package com.mouse.study.test.es.mapping;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.Geo;
import com.mouse.study.test.es.model.People;
import com.mouse.study.test.es.model.ProductTest02;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

import java.util.Calendar;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/14.
 * use to do:
 */
@Slf4j
public class MappingDemo {

    public static void main(String[] args) throws Exception {
        //log.info(JackJsonUtil.objToStr(PeopleMapping.getMapping()));
        //createBangMapping();
        testOne();
    }


    private static void getMapping() throws Exception {
        TransportClient client = ConfigService.getClient();
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices().get("safe").getMappings();
        for (ObjectObjectCursor<String, MappingMetaData> cursor : mappings) {
            System.out.println(cursor.key); // 索引下的每个type
            System.out.println(cursor.value.getSourceAsMap()); // 每个type的mapping
        }

    }

    private static void testGeoTow() throws Exception {
        TransportClient client = ConfigService.getClient();

        TestModel testModel = new TestModel();
        testModel.setName("name1");
        testModel.setAgeOne(34);
        Geo.GeoApoin location = new Geo.GeoApoin("32", "32");
        testModel.setLocation(location);
        testModel.setStartTimeOne(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        testModel.setUseOne(true);

        String str = JackJsonUtil.objToStr(testModel);

        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("name", testModel.getName())
                .field("ageOne", testModel.getAgeOne())
                .field("useOne", testModel.getUseOne())
                .field("startTimeOne", testModel.getStartTimeOne())
                .startObject("location")
                .field("lat", "10")
                .field("lon", "10")
                .endObject()
                .endObject();

        log.info("==" + builder.toString());

        IndexResponse response = client.prepareIndex("test03", "geo02")
                .setSource(builder).get();
        System.out.println(JackJsonUtil.objToStr(response.getResult()));
    }

    /**
     * 初始化geo字段类型
     *
     * @throws Exception
     */
    private static void testGeo() throws Exception {

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 10; i++) {
            Geo geo = new Geo();
            geo.setName("name" + i);
            Geo.GeoApoin location = new Geo.GeoApoin((10 + i * 3) + "", "" + (20 + i * 2));
            geo.setLocation(location);

            String str = JackJsonUtil.objToStr(geo);
            IndexResponse response = client.prepareIndex("test03", "geo01")
                    .setSource(str).get();
            System.out.println(JackJsonUtil.objToStr(response.getResult()));
        }
    }

    private static void testOne02DeleteAll() throws Exception {
        TransportClient client = ConfigService.getClient();
        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchAllQuery())
                        .source("test03")
                        .get();
        log.info("delete size:{}", response.getDeleted());

    }

    /**
     * test02 索引初始化数据
     *
     * @throws Exception
     */
    private static void testOne02Init() throws Exception {

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 50; i++) {
            ProductTest02 test02 = new ProductTest02();
            test02.setFlag(false);
            if (i % 3 == 1) {
                test02.setColor("10");
                test02.setProductName("红色" + i + "号");
                test02.setProductNo("red01");
            } else if (i % 3 == 2) {
                test02.setColor("20");
                test02.setProductName("黄色" + i + "号");
                test02.setProductNo("yellow01");
            } else {
                test02.setColor("30");
                test02.setProductName("灰色" + i + "号");
                test02.setProductNo("gray01");
                test02.setFlag(true);
            }
            test02.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
            test02.setPrice(i * 20 + i);
            test02.setUser("system");
            String str = JackJsonUtil.objToStr(test02);
            IndexResponse response = client.prepareIndex("test02", "product")
                    .setSource(str).get();
            System.out.println(JackJsonUtil.objToStr(response.getResult()));
        }
    }

    private static void testOne() throws Exception {

        Calendar instance = Calendar.getInstance();
        instance.set(2017, 11, 01, 12, 6, 00);
        Date start = instance.getTime();

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 20; i++) {
            People people = new People();
            people.setPeopleIdOne(i);
            people.setAgeOne(i + 20);
            people.setNameOne("webservice");
            people.setStartTimeOne(DateUtils.format(start, "yyyy-MM-dd HH:mm:ss.SSS"));
            people.setEndTimeOne(DateUtils.format(start, "yyyy-MM-dd HH:mm:ss.SSS"));
            people.setUseOne("true");
            if (i / 2 == 0) {
                people.setUseOne("false");
            }
            String str = JackJsonUtil.objToStr(people);
            IndexResponse response = client.prepareIndex("test01", "people2")
                    .setSource(str).get();
            System.out.println(JackJsonUtil.objToStr(response.getResult()));
        }
    }

    /**
     * 新建映射文件
     *
     * @throws Exception
     */
    public static void createBangMapping() throws Exception {
        PutMappingRequest mapping = Requests.putMappingRequest("test01").type("people2").source(People2Mapping.getMapping());
        System.out.println(mapping.source());
        TransportClient client = ConfigService.getClient();
        client.admin().indices().putMapping(mapping).actionGet();

    }
}
