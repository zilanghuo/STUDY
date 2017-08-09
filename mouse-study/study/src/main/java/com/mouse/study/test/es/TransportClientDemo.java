package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引
 */
public class TransportClientDemo {

    public static void main(String[] args) throws Exception {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                // .field("postDate", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"))
                .field("testDate", "2017-04-12 02:02:03.919")
                .field("message", "trying out Elasticsearch")
                .endObject();
        String json = builder.toString();

        Settings settings = Settings.builder()
                .put("cluster.name", "ELK-test1")
                .put("client.transport.sniff", true).build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.17.34.121"), 9300));

        IndexResponse response = client.prepareIndex("dev112", "dev", "1")
                .setSource(builder).get();

        System.out.println(JackJsonUtil.objToStr(response));

    }
}
