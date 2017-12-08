package com.mouse.study.test.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Created by lwf on 2017/8/9.
 * use to do:如何设置编码
 */
public class ConfigService {

    public static TransportClient getClient() throws Exception{
        Settings settings = Settings.builder()
                .put("cluster.name", "ELK-test1")
                .put("client.transport.sniff", true).build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.17.34.122"), 9300));

        return client;
    }


}
