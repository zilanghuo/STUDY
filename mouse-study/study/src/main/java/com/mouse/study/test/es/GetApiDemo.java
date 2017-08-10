package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
public class GetApiDemo {

    private static final Logger logger = LoggerFactory.getLogger(GetApiDemo.class);


    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        GetResponse response = client.prepareGet("dev112", "dev", "3").get();
        logger.info(response.getId());
        logger.info("-----" + response.getSource());
        logger.info(response.getId());

        System.out.println(JackJsonUtil.objToStr(response));


    }
}
