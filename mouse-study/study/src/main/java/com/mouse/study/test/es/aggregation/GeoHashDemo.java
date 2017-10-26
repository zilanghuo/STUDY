package com.mouse.study.test.es.aggregation;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoHashGrid;

/**
 * @author lwf
 * @date 2017/10/26
 * use:
 */
public class GeoHashDemo {

    public static void main(String[] args) throws Exception {
        TransportClient client = ConfigService.getClient();
        SearchResponse sr = client.prepareSearch().setIndices("safe1234")
                .setTypes("customerVisitRecord")
                .addAggregation(AggregationBuilders.geohashGrid("geoGrid").field("location").precision(2))
                .execute().get();

        GeoHashGrid agg = sr.getAggregations().get("geoGrid");
    }
}
