package com.mouse.study.test.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
@Slf4j
public class MultiGetApiDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("dev112", "dev", "1", "14")
                .add("dev111", "dev", "1", "3", "4")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
                log.info("result:" + json);
            }
        }
    }
}
