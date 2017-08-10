package com.mouse.study.test.es.search;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.test.es.model.Product;
import com.mouse.study.utils.JackJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Date;

/**
 * Created by lwf on 2017/8/10.
 * use to do:单个查询
 */
@Slf4j
public class SimpleSearch {

    public static void main(String[] args) throws Exception {
        testThree();

    }

    /**
     * 批量增加引用
     * @throws Exception
     */
    private static void testThree() throws Exception{
        TransportClient client = ConfigService.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0 ;i<10;i++){
            IndexRequestBuilder requestBuilder = client.prepareIndex("twitter", "dev");
            Product product = new Product();
            product.setProductNo("测试000"+i);
            product.setProductName("测试批量更新"+i);
            product.setUser("system");
            product.setCreateTime(new Date());
            product.setModifyTime(new Date());
            String str = JackJsonUtil.objToStr(product);
            requestBuilder.setSource(str);
            bulkRequest.add(requestBuilder);
        }
        BulkResponse response = bulkRequest.get();
        BulkItemResponse[] items = response.getItems();
        log.info("size:"+items.length);
        log.info("耗时:"+response.getTook());
        for (BulkItemResponse item : items){
            log.info(JackJsonUtil.objToStr(item.getResponse()));
        }

    }

    /**
     * 多字段条件获取值,可以多个index,可以列表
     * @throws Exception
     */
    private static void testTwo() throws Exception{
        TransportClient client = ConfigService.getClient();
        MultiGetRequest.Item item = new MultiGetRequest.Item("twitter","dev","AV3K2l4qHEzmNHcfnpT4");
        MultiGetRequest.Item item2 = new MultiGetRequest.Item("twitter","dev","AV3K185sZWCH5NmYlq1V");
        MultiGetRequest.Item item3 = new MultiGetRequest.Item("dev112","dev","29");

        MultiGetResponse response = client.prepareMultiGet()
                .add(item)
                .add(item2)
                .add(item3)
                .get();
        MultiGetItemResponse[] responses = response.getResponses();
        log.info("size:"+responses.length);
        for (MultiGetItemResponse itemResponse:responses){
            log.info(itemResponse.getIndex()+"----"+itemResponse.getType());
            log.info(JackJsonUtil.objToStr(itemResponse.getResponse()));
            log.info(itemResponse.getResponse().getSourceAsString());
        }

    }


    /**
     * 通过id获取单个记录
     * @throws Exception
     */
    private static void testOne() throws Exception{
        TransportClient client = ConfigService.getClient();
        GetResponse response = client.prepareGet("twitter", "dev", "AV3K2l4qHEzmNHcfnpT4").get();
        log.info("response【{}】",JackJsonUtil.objToStr(response));
        log.info("result【{}】",JackJsonUtil.objToStr(response.getSource()));
    }

}
