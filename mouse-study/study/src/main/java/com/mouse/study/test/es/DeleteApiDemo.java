package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by lwf on 2017/8/9.
 * use to do: 删除api
 */
public class DeleteApiDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        DeleteIndexResponse response = client.admin().indices().prepareDelete("").execute().get();
        System.out.println(JackJsonUtil.objToStr(response));
    }
}
