package com.mouse.study.test.es.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by lwf on 2017/8/14.
 * use to do:
 */
public class PeopleMapping {

    public static XContentBuilder getMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties")

                    .startObject("peopleId")
                    .field("type", "integer")
                    .endObject()

                    .startObject("age")
                    .field("type", "integer")
                    .endObject()

                    .startObject("name")
                    .field("type", "string")
                    .endObject()

                    .startObject("use")
                    .field("type", "boolean")
                    .endObject()

                    .startObject("startTime")
                    .field("type", "date")
                    .field("format","YYYY-MM-DD HH:mm:ss.SSS")
                    .endObject()

                    .startObject("endTime")
                    .field("type", "date")
                    .field("format","YYYY-MM-DD HH:mm:ss.SSS")
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
