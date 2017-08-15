package com.mouse.study.test.es.mapping;

import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.People;
import com.mouse.study.test.es.model.ProductTest02;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

import java.util.Date;

/**
 * Created by lwf on 2017/8/14.
 * use to do:
 */
@Slf4j
public class MappingDemo {

    public static void main(String[] args) throws Exception {
        //log.info(JackJsonUtil.objToStr(PeopleMapping.getMapping()));
        testOne02Init();
    }

    private static void testOne02DeleteAll()throws Exception{
        TransportClient client = ConfigService.getClient();
        BulkByScrollResponse response =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchAllQuery())
                        .source("test02")
                        .get();
        log.info("delete size:{}",response.getDeleted());

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
            if (i % 3 == 1){
                test02.setColor("10");
                test02.setProductName("红色" + i + "号");
                test02.setProductNo("red01");
            }else if (i %3 == 2){
                test02.setColor("20");
                test02.setProductName("黄色" + i + "号");
                test02.setProductNo("yellow01");
            }else{
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

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 10; i++) {
            People people = new People();
            people.setPeopleIdOne(i);
            people.setAgeOne(i + 20);
            people.setNameOne("tes" + i);
            people.setStartTimeOne(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
            people.setEndTimeOne(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
            people.setUseOne("true");
            if (i / 2 == 0) {
                people.setUseOne("false");
            }
            String str = JackJsonUtil.objToStr(people);
            IndexResponse response = client.prepareIndex("test01", "peopleThree")
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
        PutMappingRequest mapping = Requests.putMappingRequest("test02").type("product").source(ProductMapping.getMapping());
        TransportClient client = ConfigService.getClient();
        client.admin().indices().putMapping(mapping).actionGet();

    }
}
