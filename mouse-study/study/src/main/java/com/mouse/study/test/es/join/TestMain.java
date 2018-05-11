package com.mouse.study.test.es.join;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

/**
 * @author lwf
 * @date 2018/5/8
 * use:
 */
public class TestMain {

    public final static String indexName = "test-0508";

    public static void main(String[] args) throws Exception{
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("0001");
        repayInfo.setId("0001");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo("c001");
        couponOne.setAmount(BigDecimal.ONE);
        couponList.add(couponOne);
        repayInfo.setCouponList(couponList);
        System.out.println(JackJsonUtil.objToStr(repayInfo));
    }

    @org.junit.Test
    public void searchCoupon() throws Exception{
        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();
        QueryBuilder qb = nestedQuery(
                "couponList",
                boolQuery().must(QueryBuilders.matchAllQuery()),
                ScoreMode.Avg
        );
        boolQueryBuilder.must(qb);

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb);
        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(response.status());
        System.out.println(response.getHits().getTotalHits());
        System.out.println(response.getHits().getHits()[0].getSourceAsString());
        String resource = response.getHits().getHits()[0].getSourceAsString();
        RepayInfo info = (RepayInfo) JackJsonUtil.strToObj(resource,RepayInfo.class);
        System.out.println(info.getCouponList().get(0).getCouponNo());
    }

    @org.junit.Test
    public void addData() throws Exception{
        TransportClient client = ConfigService.getClient();
        String indexName = "test-0508";
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("0002");
        repayInfo.setId("0002");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo("c001");
        couponOne.setAmount(BigDecimal.ONE);
        couponList.add(couponOne);

        Coupon couponTwo = new Coupon();
        couponTwo.setCouponNo("c002");
        couponTwo.setAmount(BigDecimal.TEN);
        couponList.add(couponTwo);
        repayInfo.setCouponList(couponList);
        System.out.println(repayInfo.gainBuilder().string());

        IndexResponse insertResponse = client.prepareIndex(indexName, RepayInfoMapping.getMapperName())
                .setSource(JackJsonUtil.objToStr(repayInfo)).get();
        System.out.println(JackJsonUtil.objToStr(insertResponse));

    }

    @org.junit.Test
    public void init() throws Exception{
        String indexName = "test-0508";
        TransportClient client = ConfigService.getClient();
        try {
           // DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(indexName).get();
        }catch (Exception e){
        }
        CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).get();

        PutMappingRequest request = Requests.putMappingRequest(indexName).type(RepayInfoMapping.getMapperName()).source(RepayInfoMapping.getMapping());
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(request).actionGet();
        System.out.println("----"+ JackJsonUtil.objToStr(putMappingResponse));

        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("0002");
        repayInfo.setId("0002");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo("c001");
        couponOne.setAmount(BigDecimal.ONE);
        couponList.add(couponOne);

        Coupon couponTwo = new Coupon();
        couponTwo.setCouponNo("c002");
        couponTwo.setAmount(BigDecimal.TEN);
        couponList.add(couponTwo);
        repayInfo.setCouponList(couponList);
        System.out.println(repayInfo.gainBuilder().string());


        IndexResponse insertResponse = client.prepareIndex(indexName, RepayInfoMapping.getMapperName())
                .setSource(JackJsonUtil.objToStr(repayInfo)).get();
        System.out.println(JackJsonUtil.objToStr(insertResponse));
    }


}
