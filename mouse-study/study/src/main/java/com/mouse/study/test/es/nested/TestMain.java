package com.mouse.study.test.es.nested;

import com.mouse.study.test.es.ConfigService;
import com.mouse.study.utils.DateUtils;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNested;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketselector.BucketSelectorPipelineAggregationBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;

import java.math.BigDecimal;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

/**
 * @author lwf
 * @date 2018/5/8
 * use:
 */
public class TestMain {

    public final static String indexName = "test-0508";

    public static void main(String[] args) throws Exception {

        TransportClient client = ConfigService.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("user_dev").setTypes("userCollectInfo")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("userNo").field("userNo").size(Integer.MAX_VALUE));
        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(response.toString());
        Terms terms = response.getAggregations().get("userNo");
    }

    @org.junit.Test
    public void nestedForAggSumAndScript() throws Exception {
        Date startTime = DateUtils.plusDays(new Date(), 10);
        Date endTime = DateUtils.plusDays(new Date(), 29);

        TransportClient client = ConfigService.getClient();
        //1、 start <= validStart <= end
        QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //2、start <= validStart <= validEnd <= end
        BoolQueryBuilder subRangeBuilder = QueryBuilders.boolQuery();
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").lte(DateUtils.formatByEsForDate(endTime)));

        //3、 start <= validEnd <= end
        QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //4、 validStart <= start <= end <= validEnd
        BoolQueryBuilder subRangeBuilderTwo = QueryBuilders.boolQuery();
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.validStartTime").lte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(endTime)));

        BoolQueryBuilder nestedQueryBuilder = QueryBuilders.boolQuery();
        nestedQueryBuilder.should(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilder)
                .should(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilderTwo);

        // 针对内部查询，符合内部条件，添加：innerHit，response 体包含内嵌的命中数据体
        QueryBuilder qb = QueryBuilders.nestedQuery("couponList", nestedQueryBuilder, ScoreMode.Avg).innerHit(new InnerHitBuilder("couponNo"));

        Map<String, String> bucketsPathsMap = new HashMap(2);
        bucketsPathsMap.put("bucketAmount", "sumTwo");
        //script 条件
        Script script = new Script("params.bucketAmount >= 2 ");

        BucketSelectorPipelineAggregationBuilder bucket = new BucketSelectorPipelineAggregationBuilder("bucket_filter", bucketsPathsMap, script);

        // 涉及到nested 查询，聚合的时候，需要再重新过滤一次
        AggregationBuilder userBuilder = AggregationBuilders.terms("userTerm").field("userId").subAggregation(
                AggregationBuilders.nested("nestedTwo", "couponList")
                        .subAggregation(AggregationBuilders.terms("couponNo").field("couponNo")
                                .subAggregation(AggregationBuilders.sum("sumTwo").field("couponList.amount"))
                                .subAggregation(bucket)
                        ));

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(userBuilder);
        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.execute().actionGet();

        Terms userTerm = response.getAggregations().get("userTerm");
        for (Terms.Bucket userBucket : userTerm.getBuckets()) {
            System.out.println("userId:" + userBucket.getKeyAsString());
        }

    }


    @org.junit.Test
    public void bucketSelector() throws Exception {
        TransportClient client = ConfigService.getClient();

        AggregationBuilder aggBuilder = AggregationBuilders.terms("repayUserId").field("userId");
        Map<String, String> bucketsPathsMap = new HashMap(2);
        bucketsPathsMap.put("bucketAmount", "repayAmount");
        //script 条件
        Script script = new Script("params.bucketAmount >= 15 ");
        BucketSelectorPipelineAggregationBuilder bucket = new BucketSelectorPipelineAggregationBuilder("sales_bucket_filter", bucketsPathsMap, script);
        SumAggregationBuilder sumAgg = AggregationBuilders.sum("repayAmount").field("repayAmount");

        aggBuilder.subAggregation(sumAgg);
        aggBuilder.subAggregation(bucket);
        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .addAggregation(aggBuilder);
        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.execute().get();
        Terms termUser = response.getAggregations().get("repayUserId");
        for (Terms.Bucket termBucket : termUser.getBuckets()) {
            Sum sum = termBucket.getAggregations().get("repayAmount");
            System.out.println(termBucket.getKeyAsString() + ":" + sum.getValue());
        }
    }

    /**
     * 1、userId 去重
     * 2、内查询：时间筛选
     * 3、agg 筛选的数据，必须为nested query 命中的数据
     *
     * @throws Exception
     */
    @org.junit.Test
    public void nestedForAggSum() throws Exception {
        Date startTime = DateUtils.plusDays(new Date(), 20);
        Date endTime = DateUtils.plusDays(new Date(), 29);

        TransportClient client = ConfigService.getClient();
        //1、 start <= validStart <= end
        QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //2、start <= validStart <= validEnd <= end
        BoolQueryBuilder subRangeBuilder = QueryBuilders.boolQuery();
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").lte(DateUtils.formatByEsForDate(endTime)));

        //3、 start <= validEnd <= end
        QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //4、 validStart <= start <= end <= validEnd
        BoolQueryBuilder subRangeBuilderTwo = QueryBuilders.boolQuery();
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.validStartTime").lte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(endTime)));
        BoolQueryBuilder nestedQueryBuilder = QueryBuilders.boolQuery();
        nestedQueryBuilder.should(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilder)
                .should(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilderTwo);

        // 针对内部查询，符合内部条件，添加：innerHit，response 体包含内嵌的命中数据体
        QueryBuilder qb = QueryBuilders.nestedQuery("couponList", nestedQueryBuilder, ScoreMode.Avg).innerHit(new InnerHitBuilder("couponNo"));

        // 涉及到nested 查询，聚合的时候，需要再重新过滤一次
        AggregationBuilder userBuilder = AggregationBuilders.terms("userTerm").field("userId")
                .subAggregation(AggregationBuilders.nested("nestedTwo", "couponList")
                        .subAggregation(AggregationBuilders.filter("filterOne", nestedQueryBuilder)
                                .subAggregation(AggregationBuilders.sum("sumTwo").field("couponList.amount")
                                )));

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(userBuilder);
        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.execute().actionGet();

        Terms userTerm = response.getAggregations().get("userTerm");
        for (Terms.Bucket term : userTerm.getBuckets()) {
            Nested nested = term.getAggregations().get("nestedTwo");
            Filter filterOne = nested.getAggregations().get("filterOne");
            Sum sum = filterOne.getAggregations().get("sumTwo");
            System.out.println("userId:" + term.getKeyAsString() + ",amount:" + sum.getValue());
        }
    }

    /**
     * 1、userId 去重
     * 2、内查询：时间筛选
     * 3、求所有记录的红包金额，除以总用户数
     * 4、根据金额筛选
     * 5、增加内嵌的数据返回体
     *
     * @throws Exception
     */
    @org.junit.Test
    public void sumNested() throws Exception {
        Date startTime = DateUtils.plusDays(new Date(), 25);
        Date endTime = DateUtils.plusDays(new Date(), 29);

        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();
        //1、 start <= validStart <= end
        QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //2、start <= validStart <= validEnd <= end
        BoolQueryBuilder subRangeBuilder = QueryBuilders.boolQuery();
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilder.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").lte(DateUtils.formatByEsForDate(endTime)));

        //3、 start <= validEnd <= end
        QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime));

        //4、 validStart <= start <= end <= validEnd
        BoolQueryBuilder subRangeBuilderTwo = QueryBuilders.boolQuery();
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.validStartTime").lte(DateUtils.formatByEsForDate(startTime)));
        subRangeBuilderTwo.must(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(endTime)));
        BoolQueryBuilder nestedQueryBuilder = QueryBuilders.boolQuery();
        nestedQueryBuilder.should(QueryBuilders.rangeQuery("couponList.validStartTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilder)
                .should(QueryBuilders.rangeQuery("couponList.ValidEndTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime)))
                .should(subRangeBuilderTwo);

        // 针对内部查询，符合内部条件，添加：innerHit，response 体包含内嵌的命中数据体
        QueryBuilder qb = QueryBuilders.nestedQuery("couponList", nestedQueryBuilder, ScoreMode.Avg).innerHit(new InnerHitBuilder("couponNo"));

        boolQueryBuilder.must(qb);
        AggregationBuilder userBuilder = AggregationBuilders.terms("userTerm").field("userId")
                .subAggregation(AggregationBuilders.nested("nestedTwo", "couponList")
                        .subAggregation(AggregationBuilders.terms("couponNo").field("couponList.couponNo").subAggregation(AggregationBuilders.sum("sumTwo").field("couponList.amount"))));

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .addAggregation(userBuilder);
        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(response.getHits().getTotalHits());

        //获取nested 查询命中的记录,存在条数限制
        Map<String, SearchHits> innerHits = response.getHits().getHits()[0].getInnerHits();
        Iterator<Map.Entry<String, SearchHits>> iterator = innerHits.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue().getHits()[0].getSourceAsString());
        }

        // 记录数
        Terms userTerm = response.getAggregations().get("userTerm");
        for (Terms.Bucket bucket : userTerm.getBuckets()) {
            Nested nested = bucket.getAggregations().get("nestedTwo");
            Terms couponTerm = nested.getAggregations().get("couponNo");
            for (Terms.Bucket bucket1 : couponTerm.getBuckets()) {
                Sum sum = bucket1.getAggregations().get("sumTwo");
                System.out.println("userId:" + bucket.getKeyAsString() + ",sumAmount:" + sum.getValue());
            }
        }
    }

    @org.junit.Test
    public void reverseNestedTwo() throws Exception {
        TransportClient client = ConfigService.getClient();
        BoolQueryBuilder boolQueryBuilder =
                QueryBuilders.boolQuery();

        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 22);
        Date startTime = DateUtils.plusDays(new Date(), 45);

        now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 26);
        Date endTime = DateUtils.plusDays(new Date(), 55);

        QueryBuilder qb = nestedQuery(
                "couponList",
                QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("couponList.userTime").gte(DateUtils.formatByEsForDate(startTime)).lte(DateUtils.formatByEsForDate(endTime))),
                ScoreMode.Avg);
        boolQueryBuilder.must(qb);

        AggregationBuilder userBuilder = AggregationBuilders.terms("userTerm").field("userId")
                .subAggregation(AggregationBuilders.nested("nestedTwo", "couponList")
                        .subAggregation(AggregationBuilders.dateRange("dataRange").field("couponList.userTime").addRange(DateUtils.formatByEsForDate(startTime), DateUtils.formatByEsForDate(endTime))
                                .subAggregation(AggregationBuilders.sum("sumTwo").field("couponList.amount"))
                                .subAggregation(AggregationBuilders.reverseNested("reverseAgg")))
                );

        SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(RepayInfoMapping.getMapperName())
                .setQuery(qb)
                .addAggregation(userBuilder)
                .setSize(100);
        System.out.println(requestBuilder.toString());

        SearchResponse response = requestBuilder.execute().actionGet();
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
                //需要加层判断，range会为null
                Sum sum = range.getAggregations().get("sumTwo");
                ReverseNested reverseNested = range.getAggregations().get("reverseAgg");
                System.out.println(reverseNested.getDocCount());
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

        AggregationBuilder aggBuilder =
                AggregationBuilders.nested("nestedAgg", "couponList")
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
    public void addData() throws Exception {
        TransportClient client = ConfigService.getClient();
        String indexName = "test-0508";
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (int i = 0; i < 10; i++) {
            RepayInfo repayInfo = new RepayInfo();
            repayInfo.setUserId("0004" + i);
            repayInfo.setId("0004" + i);
            repayInfo.setRepayAmount(new BigDecimal(new Random().nextInt(20)));
            List<Coupon> couponList = new ArrayList();
            Coupon couponOne = new Coupon();
            couponOne.setCouponNo(repayInfo.getUserId() + "c001");
            couponOne.setAmount(new BigDecimal(new Random().nextInt(20)));
            couponOne.setValidStartTime(DateUtils.plusDays(new Date(), 18 - new Random().nextInt(5)));
            couponOne.setValidEndTime(DateUtils.plusDays(new Date(), 22 - new Random().nextInt(5)));
            couponList.add(couponOne);

            repayInfo.setCouponList(couponList);
            bulkRequest.add(client.prepareIndex(indexName, RepayInfoMapping.getMapperName(), repayInfo.getId())
                    .setSource(repayInfo.gainBuilder()));
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();

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
        repayInfo.setUserId("0003");
        repayInfo.setId("0002");
        repayInfo.setRepayAmount(BigDecimal.TEN);
        List<Coupon> couponList = new ArrayList();
        Coupon couponOne = new Coupon();
        couponOne.setCouponNo(repayInfo.getUserId() + "c001");
        couponOne.setAmount(new BigDecimal(5));
        couponOne.setValidStartTime(DateUtils.plusDays(new Date(), 18));
        couponOne.setValidEndTime(DateUtils.plusDays(new Date(), 22));
        couponList.add(couponOne);

        Coupon couponTwo = new Coupon();
        couponTwo.setCouponNo(repayInfo.getUserId() + "c002");
        couponTwo.setAmount(new BigDecimal(22));
        couponTwo.setValidStartTime(DateUtils.plusDays(new Date(), 20));
        couponTwo.setValidEndTime(DateUtils.plusDays(new Date(), 23));
        couponList.add(couponTwo);

        Coupon couponTwo3 = new Coupon();
        couponTwo3.setCouponNo(repayInfo.getUserId() + "c003");
        couponTwo3.setAmount(BigDecimal.TEN);
        couponTwo3.setValidStartTime(DateUtils.plusDays(new Date(), 22));
        couponTwo3.setValidEndTime(DateUtils.plusDays(new Date(), 26));
        couponList.add(couponTwo3);
        repayInfo.setCouponList(couponList);

        System.out.println(repayInfo.gainBuilder().string());
        IndexResponse insertResponse = client.prepareIndex(indexName, RepayInfoMapping.getMapperName())
                .setSource(repayInfo.gainBuilder()).get();
        System.out.println(JackJsonUtil.objToStr(insertResponse));
    }
}
