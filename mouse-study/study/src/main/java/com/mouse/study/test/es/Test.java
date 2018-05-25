package com.mouse.study.test.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * @author lwf
 * @date 2018/5/22
 * use:
 */
public class Test {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        DeleteResponse deleteResponse = client.prepareDelete().setIndex("user").setType("userConfig").setId("delayConsumeKafkaForUserCollect")
                .execute().actionGet();
        System.out.println(deleteResponse.status());
    }

}
