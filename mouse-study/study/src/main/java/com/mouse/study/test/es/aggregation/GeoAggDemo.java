package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoHashGrid;
import org.elasticsearch.search.aggregations.bucket.range.Range;

/**
 * Created by lwf on 2017/8/16.
 * use to do: 地图类型
 */
@Slf4j
public class GeoAggDemo {

    public static void main(String[] args) throws Exception {
        testHashGrid();
    }

    private static void testFilter() throws Exception {
        TransportClient client = ConfigService.getClient();

        SearchResponse sr = client.prepareSearch()
                .setIndices("test03")
                .setTypes("geo01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.filter("filter", QueryBuilders.matchAllQuery()))
                .setSize(50)
                .execute().actionGet();

    }

    /**
     * 网格聚合
     * @throws Exception
     */
    private static void testHashGrid() throws Exception {
        TransportClient client = ConfigService.getClient();
        AggregationBuilder aggregation =
                AggregationBuilders
                        .geohashGrid("agg")
                        .field("location")
                        .precision(1).size(20);

        SearchResponse sr = client.prepareSearch()
                .setIndices("test03")
                .setTypes("geo01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(aggregation)
                .setSize(50)
                .execute().actionGet();
        GeoHashGrid  agg = sr.getAggregations().get("agg");
        for (GeoHashGrid.Bucket entry : agg.getBuckets()) {
            String keyAsString = entry.getKeyAsString(); // key as String
            GeoPoint key = (GeoPoint) entry.getKey();    // key as geo point
            long docCount = entry.getDocCount();         // Doc count
            log.info("key [{}], point {}, doc_count [{}]", keyAsString, key, docCount);
        }
    }

    /**
     * geo 距离聚合
     *
     * @throws Exception
     */
    private static void testDistance() throws Exception {
        TransportClient client = ConfigService.getClient();
        AggregationBuilder aggregation =
                AggregationBuilders
                        .geoDistance("agg", new GeoPoint(10, 50))
                        .field("location")
                        .unit(DistanceUnit.KILOMETERS)
                        .addUnboundedTo(300.0)
                        .addRange(300.0, 500.0)
                        .addUnboundedFrom(500);

        SearchResponse sr = client.prepareSearch()
                .setIndices("test03")
                .setTypes("geo01")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(aggregation)
                .setSize(50)
                .execute().actionGet();
        Range agg = sr.getAggregations().get("agg");
        for (Range.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();    // key as String
            Number from = (Number) entry.getFrom(); // bucket from value
            Number to = (Number) entry.getTo();     // bucket to value
            long docCount = entry.getDocCount();    // Doc count
            log.info("key [{}], from [{}], to [{}], doc_count [{}]", key, from, to, docCount);
        }

    }
}
