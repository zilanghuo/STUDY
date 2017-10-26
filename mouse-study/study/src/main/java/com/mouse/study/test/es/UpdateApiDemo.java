package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
@Slf4j
public class UpdateApiDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("bc_dev");
        updateRequest.type("businessFeature");
        updateRequest.id("S00001_001");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("id", "S00001_001")
                .endObject());
        UpdateResponse response = client.update(updateRequest).get();
        log.info(JackJsonUtil.objToStr(response.getResult()));

    }
}
