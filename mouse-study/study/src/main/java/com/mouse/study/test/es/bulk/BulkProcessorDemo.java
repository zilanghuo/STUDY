package com.mouse.study.test.es.bulk;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.Product;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.util.Date;

/**
 * Created by lwf on 2017/8/10.
 * use to do:多线程处理器
 */
@Slf4j
public class BulkProcessorDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();

        // 监听client 对象，弄成单例模式
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {
                        log.info("beforeBulk");
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                        log.info("afterBulk");
                        log.info("response:{}", response.getTook());
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {
                        log.info("throw Exception");
                    }
                })
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        Product product = new Product();
        product.setProductNo("bulkProcessor");
        product.setProductName("bulkProcessor");
        product.setUser("system");
        product.setCreateTime(new Date());
        String str = JackJsonUtil.objToStr(product);

        bulkProcessor.add(new IndexRequest("twitter", "dev").source(str));
        bulkProcessor.add(new IndexRequest("twitter", "dev").source(str));

        bulkProcessor.flush();

        client.prepareGet("twitter", "dev", "AV3K2l4qHEzmNHcfnpT4").get();
        bulkProcessor.flush();
        Thread.sleep(5000);

    }
}
