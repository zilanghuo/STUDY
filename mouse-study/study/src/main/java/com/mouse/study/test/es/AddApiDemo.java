package com.mouse.study.test.es;

import com.mouse.study.test.es.model.Product;
import com.mouse.study.test.es.model.ProductTest02;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
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

    /**
     * 批量增加
     *
     * @throws Exception
     */
    private static void testBatch() throws Exception {

        TransportClient client = ConfigService.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < 100000; i++) {
            IndexRequestBuilder requestBuilder = client.prepareIndex("test03", "product01");
            ProductTest02 test02 = new ProductTest02();
            test02.setFlag(false);
            if (i % 3 == 1) {
                test02.setColor("10");
                test02.setProductName("红色" + i + "号");
                test02.setProductNo("red01");
                test02.setCreateTime( "2017-06-04 21:23:03.111");
            } else if (i % 3 == 2) {
                test02.setColor("20");
                test02.setProductName("黄色" + i + "号");
                test02.setProductNo("yellow01");
                test02.setCreateTime( "2017-07-15 04:05:32.111");
            } else {
                test02.setColor("30");
                test02.setProductName("灰色" + i + "号");
                test02.setProductNo("gray01");
                test02.setFlag(true);
                test02.setCreateTime( "2017-05-06 23:02:13.111");
            }
            //test02.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
            test02.setPrice(i * 20 + i);
            test02.setUser("system");
            String str = JackJsonUtil.objToStr(test02);
            requestBuilder.setSource(str);
            bulkRequest.add(requestBuilder);
        }
        BulkResponse response = bulkRequest.get();
        BulkItemResponse[] items = response.getItems();
        log.info("size:" + items.length);
        log.info("耗时:" + response.getTook());
        log.info("result:{}", response.hasFailures());
    }

    private static void testNo() throws Exception {
        for (int i = 0; i < 50; i++) {
            Product product = new Product();
            product.setProductName("name000" + i);
            if (i % 6 == 0) {
                product.setProductNo("0001");
            } else if (i % 7 == 0) {
                product.setProductNo("0002");
            } else {
                product.setProductNo("0003");
            }
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
               // .field("endTime", "2017-04-12 02:02:03.919")
                .endObject();
        TransportClient client = ConfigService.getClient();
        IndexResponse response = client.prepareIndex("test111","testlog")
                .setId("AV0rRzvVZWCH5NmYlLjc")
                .setSource(builder).get();
        System.out.println(response.toString());
    }
}
