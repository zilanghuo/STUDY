package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
public class AddApiDemo {

    public static void main(String[] args) throws Exception {
       XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "four")
                // .field("postDate", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"))
                .field("testDate", "2017-04-12 02:02:03.919")
                .field("message", "three")
                .endObject();

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 10; i++) {
            IndexResponse response = client.prepareIndex("dev112", "dev", i+20+"")
                    .setSource(builder).get();
            System.out.println(JackJsonUtil.objToStr(response.getResult()));
        }


    }
}
