package com.mouse.study.test.testOne;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laiwufa
 * @date 2018/10/12
 * use:
 */
@Data
public class PageResult<T> implements Serializable {

    private Integer pageNo;

    /**
     * 列表
     */
    private List<T> dataList = new ArrayList();
}
