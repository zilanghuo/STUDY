package com.mouse.study.test.es.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by lwf on 2017/8/10.
 * use to do:测试模型
 */
@Data
public class Product {

    private String productNo;

    private String productName;

    private String user;

    private Date createTime;

    private Date  modifyTime;
}
