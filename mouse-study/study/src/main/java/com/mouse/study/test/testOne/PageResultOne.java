package com.mouse.study.test.testOne;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laiwufa
 * @date 2018/10/12
 * use:
 */
@Data
public class PageResultOne {

    private Integer pageNo;

    /**
     * 列表
     */
    private String dataList;
}
