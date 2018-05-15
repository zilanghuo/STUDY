package com.mouse.study.test.es.nested;

import com.mouse.study.utils.DateUtils;
import lombok.Data;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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

    private List<Coupon> couponList ;

    public  XContentBuilder gainBuilder() {
        try {
            XContentBuilder builder = jsonBuilder().startObject();

            builder.field("id", this.id)
                    .field("userId", this.userId)
                    .field("repayAmount", this.repayAmount)
                    .startArray("couponList");
            for (int i = 0; i < couponList.size(); i++) {
                builder.startObject();
                    builder.field("couponNo",couponList.get(i).getCouponNo());
                    builder.field("amount",couponList.get(i).getAmount());
                    builder.field("userTime", DateUtils.formatByEsForDate(couponList.get(i).getUserTime()));
                builder.endObject();
            }
            builder.endArray();
            builder.endObject();
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
