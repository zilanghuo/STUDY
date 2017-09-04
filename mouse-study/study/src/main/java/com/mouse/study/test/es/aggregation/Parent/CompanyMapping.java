package com.mouse.study.test.es.aggregation.Parent;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/9/4.
 * use to do:
 */
public class CompanyMapping {

    public static XContentBuilder getMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("mappings")

                    .startObject("branch")
                    .endObject()

                    .startObject("employee")
                    .startObject("_parent")
                        .field("type","branch")
                    .endObject()
                    .endObject()

                    //关联数据
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }

}
