package com.mouse.study.test.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/14.
 * use to do:
 */
public class People2Mapping {

    public static XContentBuilder getMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties")

                    .startObject("peopleIdOne")
                    .field("type", "integer")
                    .endObject()

                    .startObject("ageOne")
                    .field("type", "integer")
                    .endObject()

                    .startObject("nameOne")
                     .field("type", "keyword")
                    .endObject()

                    .startObject("useOne")
                    .field("type", "boolean")
                    .endObject()

                    .startObject("startTimeOne")
                    .field("type", "date")
                    .field("format","yyyy-MM-dd HH:mm:ss.SSS")
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
