package com.mouse.study.test.es.join;

import lombok.Data;
import net.sf.json.JSONObject;

import java.math.BigDecimal;

/**
 * @author lwf
 * @date 2018/5/11
 * use:
 */
@Data
public class Coupon {

    private String couponNo;

    private BigDecimal amount;

    @Override
    public String toString(){
        JSONObject json = JSONObject.fromObject(this);
        return json.toString();
    }

}
