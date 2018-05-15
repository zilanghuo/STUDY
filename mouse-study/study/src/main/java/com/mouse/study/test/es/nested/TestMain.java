package com.mouse.study.test.es.nested;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.DateUtils;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNested;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.collapse.CollapseBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

/**
 * @author lwf
 * @date 2018/5/8
 * use:
 */
public class TestMain {

    public final static String indexName = "test-0508";

    public static void main(String[] args) throws Exception {
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("0001");
        repayInfo.setId("0001");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo("c001");
        couponOne.setAmount(BigDecimal.ONE);
        couponOne.setUserTime(new Date());
        couponList.add(couponOne);
        repayInfo.setCouponList(couponList);
        System.out.println(JackJsonUtil.objToStr(repayInfo));
    }


    /**
     * 1、userId 去重
     * 2、内查询：时间筛选
     * 3、求所有记录的红包金额，除以总用户数
     * 4、根据金额筛选
     *
     * @throws Exception
     */
    @org.junit.Test
    public void sumNested() throws Exception {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 22);
        Date startTime = DateUtils.plusDays(new Date(), 45);

        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 26);
        Date endTime = DateUtils.plusDays(new Date(), 55);

        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();

        // 针对内部查询，符合内部条件
        QueryBuilder qb = nestedQuery(
                "couponList",
                QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("couponList.userTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime))),
                ScoreMode.Avg);
        boolQueryBuilder.must(qb);

        AggregationBuilder userBuilder = AggregationBuilders.terms("userTerm").field("userId")
                .subAggregation(AggregationBuilders.nested("nestedTwo", "couponList")
                        .subAggregation(AggregationBuilders.dateRange("dataRange").field("couponList.userTime").addRange(DateUtils.formatByEsForDate(startTime), DateUtils.formatByEsForDate(endTime))
                                .subAggregation(AggregationBuilders.sum("sumTwo").field("couponList.amount")))
                );

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .addAggregation(userBuilder)
                .setCollapse(new CollapseBuilder("userId"));
        System.out.println(requestBuilder.toString());

        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(response.getHits().getTotalHits());
        SearchHit[] hits = response.getHits().getHits();
        // 记录数
        for (int i = 0; i < hits.length; i++) {
            SearchHit hit = hits[i];
            RepayInfo info = (RepayInfo) JackJsonUtil.strToObj(hit.getSourceAsString(), RepayInfo.class);
            System.out.println(JackJsonUtil.objToStr(info));
        }
        Terms userTerm = response.getAggregations().get("userTerm");
        for (Terms.Bucket bucket : userTerm.getBuckets()) {
            Nested nested = bucket.getAggregations().get("nestedTwo");
            Range dateRange = nested.getAggregations().get("dataRange");
            for (Range.Bucket range : dateRange.getBuckets()) {
                System.out.println(range.getKeyAsString() + ",size:" + range.getDocCount());
                Sum sum = range.getAggregations().get("sumTwo");
                // 获取到符合条件的红包总金额
                System.out.println("userId:" + bucket.getKeyAsString() + ",totalAmount:" + sum.getValue() / bucket.getDocCount());
            }
        }

    }

    @org.junit.Test
    public void reverseNested() throws Exception {
        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();

        QueryBuilder qb = nestedQuery(
                "couponList",
                QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()),
                ScoreMode.Avg);
        boolQueryBuilder.must(qb);

        AggregationBuilder aggBuilder = AggregationBuilders.nested("nestedAgg", "couponList")
                .subAggregation(AggregationBuilders.terms("couponTerm").field("couponList.couponNo")
                        .subAggregation(AggregationBuilders.reverseNested("reverseAgg")));

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .addAggregation(aggBuilder)
                .setCollapse(new CollapseBuilder("userId"));
        SearchResponse response = requestBuilder.execute().actionGet();
        Nested nestedAgg = response.getAggregations().get("nestedAgg");
        //内嵌的红包类别数
        Terms name = nestedAgg.getAggregations().get("couponTerm");
        System.out.println("红包编号有" + name.getBuckets().size() + "个");
        for (Terms.Bucket bucket : name.getBuckets()) {
            // 每个红包出现的次数
            ReverseNested resellerToProduct = bucket.getAggregations().get("reverseAgg");
            System.out.println("红包编号：" + bucket.getKeyAsString() + "，出现了" + resellerToProduct.getDocCount() + "次");
        }

    }

    @org.junit.Test
    public void searchCoupon() throws Exception {
        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();
        BoolQueryBuilder nestedBoolQuery =
                QueryBuilders.boolQuery();
        QueryBuilder qb = nestedQuery(
                "couponList",
                nestedBoolQuery.must(QueryBuilders.matchAllQuery()),
                ScoreMode.Avg);
        boolQueryBuilder.must(qb);

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .setCollapse(new CollapseBuilder("userId"))
                .addAggregation(AggregationBuilders.terms("userId").field("userId").subAggregation(AggregationBuilders.nested("nested", "couponList").subAggregation(AggregationBuilders.sum("amount"))));
        SearchResponse response = requestBuilder.execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        for (int i = 0; i < hits.length; i++) {
            System.out.println(JackJsonUtil.objToStr(hits[i].getSourceAsString()));
        }
        Terms termUserId = response.getAggregations().get("userId");

        String resource = response.getHits().getHits()[0].getSourceAsString();
        RepayInfo info = (RepayInfo) JackJsonUtil.strToObj(resource, RepayInfo.class);
        System.out.println(info.getCouponList().get(0).getCouponNo());
    }

    @org.junit.Test
    public void addData() throws Exception {
        TransportClient client = ConfigService.getClient();
        String indexName = "test-0508";
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("20180515004");
        repayInfo.setId("20180515004");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo(repayInfo.getUserId() + "c001");
        couponOne.setAmount(new BigDecimal(20));
        couponOne.setUserTime(DateUtils.plusDays(new Date(), 40));
        couponList.add(couponOne);

        Coupon couponTwo = new Coupon();
        couponTwo.setCouponNo(repayInfo.getUserId() + "c002");
        couponTwo.setAmount(new BigDecimal(15));
        couponTwo.setUserTime(DateUtils.plusDays(new Date(), 50));
        couponList.add(couponTwo);
        repayInfo.setCouponList(couponList);
        System.out.println(repayInfo.gainBuilder().string());

        IndexResponse insertResponse = client.prepareIndex(indexName, RepayInfoMapping.getMapperName())
                .setSource(repayInfo.gainBuilder()).get();
        System.out.println(JackJsonUtil.objToStr(insertResponse));

    }

    @org.junit.Test
    public void init() throws Exception {
        String indexName = "test-0508";
        TransportClient client = ConfigService.getClient();
        try {
            DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(indexName).get();
        } catch (Exception e) {
        }
        CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).get();

        PutMappingRequest request = Requests.putMappingRequest(indexName).type(RepayInfoMapping.getMapperName()).source(RepayInfoMapping.getMapping());
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(request).actionGet();
        System.out.println("----" + JackJsonUtil.objToStr(putMappingResponse));

        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setUserId("0002");
        repayInfo.setId("0002");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo("c001");
        couponOne.setAmount(BigDecimal.ONE);
        couponOne.setUserTime(new Date());
        couponList.add(couponOne);

        Coupon couponTwo = new Coupon();
        couponTwo.setCouponNo("c002");
        couponTwo.setAmount(BigDecimal.TEN);
        couponTwo.setUserTime(new Date());
        couponList.add(couponTwo);
        repayInfo.setCouponList(couponList);
        System.out.println(repayInfo.gainBuilder().string());


        IndexResponse insertResponse = client.prepareIndex(indexName, RepayInfoMapping.getMapperName())
                .setSource(repayInfo.gainBuilder()).get();
        System.out.println(JackJsonUtil.objToStr(insertResponse));
    }


}
