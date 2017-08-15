package com.mouse.study.test.es;

import com.mouse.study.test.es.model.Product;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/9.
 * use to do:建立索引，发送数据
 */
@Slf4j
public class AddApiDemo {

    public static void main(String[] args) throws Exception {
        testOne();
    }

    private static void testNo() throws Exception {
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setProductName("name000" + i);
            if (i % 6 == 0) {
                product.setProductNo("0001");
            } else if (i % 7 == 0) {
                product.setProductNo("0002");
            } else
                product.setProductNo("0003");

            product.setUser("system");
            product.setCreateTime(new Date());
            TransportClient client = ConfigService.getClient();
            String str = JackJsonUtil.objToStr(product);
            IndexResponse response = client.prepareIndex("test01", "product").setSource(str).get();
            log.info("response【{}】", JackJsonUtil.objToStr(response));
        }
    }

    private static void testSendByMapping() throws Exception {
        TransportClient client = ConfigService.getClient();
        client.admin().indices();
    }

    private static void testOne() throws Exception {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                // .field("postDate", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"))
                .field("endTime", "2017-04-12 02:02:03.919")
                .endObject();

        TransportClient client = ConfigService.getClient();
        IndexResponse response = client.prepareIndex("test01", "dateTest")
                .setSource(builder).get();
        System.out.println(JackJsonUtil.objToStr(response.getResult()));
    }
}
