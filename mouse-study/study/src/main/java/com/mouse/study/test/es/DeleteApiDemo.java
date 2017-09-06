package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lwf on 2017/8/9.
 * use to do: 删除api
 */
public class DeleteApiDemo {

    private static final Logger logger = LoggerFactory.getLogger(DeleteApiDemo.class);


    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        DeleteIndexResponse response = client.admin().indices().prepareDelete("dmp").execute().get();
        System.out.println(JackJsonUtil.objToStr(response));

    }
}
