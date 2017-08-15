package com.mouse.study.test.es.model;

import lombok.Data;

/**
 * Created by lwf on 2017/8/10.
 * use to do:测试模型
 */
@Data
public class ProductTest02 {

    private String productNo;

    private String productName;

    private String user;

    private String color;

    private Integer price; //integer

    private Boolean flag;

    private String createTime; //Date类型

}
