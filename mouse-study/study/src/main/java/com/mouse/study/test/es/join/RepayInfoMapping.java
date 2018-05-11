package com.mouse.study.test.es.join;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author lwf
 * @date 2018/5/8
 * use:
 */
public class RepayInfoMapping {

    public static XContentBuilder getMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties")

                    .startObject("id")
                    .field("type", "text")
                    .endObject()

                    .startObject("userId")
                    .field("type", "keyword")
                    .endObject()

                    .startObject("repayAmount")
                    .field("type", "double")
                    .endObject()

                    .startObject("couponList")
                    .field("type", "nested")
                    .endObject()

                    //关联数据
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapping;
    }


    public static String getMapperName() {
        return "repayInfo2";
    }
}
