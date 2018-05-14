package com.mouse.study.test.es.nested;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lwf
 * @date 2018/5/11
 * use:
 */
@Data
public class Coupon {

    private String couponNo;

    private BigDecimal amount;

    private Date userTime;

}
