package com.mouse.study.test.es.mapping;

import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.People;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Date;

/**
 * Created by lwf on 2017/8/14.
 * use to do:
 */
@Slf4j
public class MappingDemo {

    public static void main(String[] args) throws Exception {
        //log.info(JackJsonUtil.objToStr(PeopleMapping.getMapping()));
        testOne();
    }

    private static void testOne() throws Exception {

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 10; i++) {
            People people = new People();
            people.setPeopleIdOne(i);
            people.setAgeOne(i + 10);
            people.setNameOne("hello" + i);
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
        PutMappingRequest mapping = Requests.putMappingRequest("test01").type("peopleThree").source(PeopleMapping.getMapping());
        TransportClient client = ConfigService.getClient();
        client.admin().indices().putMapping(mapping).actionGet();

    }
}
