package com.mouse.study.test.es.aggregation.Parent;

import com.mouse.study.test.es.ConfigService;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by lwf on 2017/9/4.
 * use to do:父类嵌套查询
 */
public class ParentAggDemo {

    public static void main(String[] args) throws Exception{
        initMapping();
    }

    public static void initMapping() throws Exception{
        PutMappingRequest mapping = Requests.putMappingRequest("test01").type("company").source(CompanyMapping.getMapping());
        TransportClient client = ConfigService.getClient();
        client.admin().indices().putMapping(mapping).actionGet();
    }

}
