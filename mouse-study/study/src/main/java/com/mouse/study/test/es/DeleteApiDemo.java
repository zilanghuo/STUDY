package com.mouse.study.test.es;

import com.mouse.study.utils.JackJsonUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lwf on 2017/8/9.
 * use to do:
 */
public class DeleteApiDemo {

    private static final Logger logger = LoggerFactory.getLogger(DeleteApiDemo.class);


    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        DeleteResponse response = client.prepareDelete("dev112", "dev", "1").get();
        logger.info(response.getId());
        logger.info("-----" + response.getResult());
        logger.info(response.getId());

        System.out.println(JackJsonUtil.objToStr(response));


    }
}
