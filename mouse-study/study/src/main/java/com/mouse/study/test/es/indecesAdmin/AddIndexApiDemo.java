package com.mouse.study.test.es.indecesAdmin;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
@Slf4j
public class AddIndexApiDemo {

    public static void main(String[] args) throws Exception{

        TransportClient client = ConfigService.getClient();
        IndicesAdminClient adminClient = client.admin().indices();
        CreateIndexResponse response = adminClient.prepareCreate("twitter").setSettings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2))
                .get();
        log.info(JackJsonUtil.objToStr(response));

    }
}
