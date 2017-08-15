package com.mouse.study.test.es.bulk;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.Product;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Date;

/**
 * Created by lwf on 2017/8/10.
 * use to do:批量方法
 */
@Slf4j
public class BulkDemo {

    public static void main(String[] args) {

    }

    /**
     * 批量增加引用
     *
     * @throws Exception
     */
    private static void testThree() throws Exception {
        TransportClient client = ConfigService.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < 10; i++) {
            IndexRequestBuilder requestBuilder = client.prepareIndex("twitter", "dev");
            Product product = new Product();
            product.setProductNo("测试000" + i);
            product.setProductName("测试批量更新" + i);
            product.setUser("system");
            product.setCreateTime(new Date());
            String str = JackJsonUtil.objToStr(product);
            requestBuilder.setSource(str);
            bulkRequest.add(requestBuilder);
        }
        BulkResponse response = bulkRequest.get();
        BulkItemResponse[] items = response.getItems();
        log.info("size:" + items.length);
        log.info("耗时:" + response.getTook());
        for (BulkItemResponse item : items) {
            log.info(JackJsonUtil.objToStr(item.getResponse()));
        }

    }
}
