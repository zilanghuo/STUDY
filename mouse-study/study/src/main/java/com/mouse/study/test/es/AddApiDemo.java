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

        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setProductNo("000"+i);
            product.setProductName("产品000"+i);
            product.setUser("system");
            product.setCreateTime(new Date());
            product.setModifyTime(new Date());
            TransportClient client = ConfigService.getClient();
            String str = JackJsonUtil.objToStr(product);
            IndexResponse response = client.prepareIndex("twitter", "dev").setSource(str).get();
            log.info("response【{}】",JackJsonUtil.objToStr(response));
        }


    }

    private static void testOne() throws Exception {
        XContentBuilder builder = jsonBuilder()
                 .startObject()
                 .field("user", "four")
                 // .field("postDate", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"))
                 .field("testDate", "2017-04-12 02:02:03.919")
                 .field("message", "three")
                 .endObject();

        TransportClient client = ConfigService.getClient();
        for (int i = 0; i < 10; i++) {
            IndexResponse response = client.prepareIndex("twitter", "dev")
                    .setSource(builder).get();
            System.out.println(JackJsonUtil.objToStr(response.getResult()));
        }
    }
}
