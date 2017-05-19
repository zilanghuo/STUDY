package com.zdmoney.message.api.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rui on 15/8/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements Serializable {

    private static final long serialVersionUID = -2286851832616746971L;
    protected Integer id;

    protected Date createTime = new Date();

    protected Date modifyTime;

    protected String operator;
}
