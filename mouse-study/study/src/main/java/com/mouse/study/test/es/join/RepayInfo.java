package com.mouse.study.test.es.join;

import lombok.Data;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author lwf
 * @date 2018/5/8
 * use:
 */
@Data
public class RepayInfo implements Serializable {

    private String id;

    private String userId;

    private BigDecimal repayAmount;

    private List<Coupon> couponList = new ArrayList();

    public  XContentBuilder gainBuilder() {
        try {
            XContentBuilder builder = jsonBuilder().startObject();
            builder.field("id", this.id)
                    .field("userId", this.userId)
                    .field("repayAmount", this.repayAmount)
                    .field("couponList", this.couponList);
            builder.endObject();
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
